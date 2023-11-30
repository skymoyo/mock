package work.skymoyo.mock.client.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import work.skymoyo.mock.client.spi.CompileManager;
import work.skymoyo.mock.client.spi.MockCompile;
import work.skymoyo.mock.client.utils.BeanMockUtil;
import work.skymoyo.mock.client.utils.MockContextUtil;
import work.skymoyo.mock.common.enums.OptType;
import work.skymoyo.mock.common.exception.MockException;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mock.rpc.config.MockConf;
import work.skymoyo.mock.rpc.netty.ClientInitializer;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@ConditionalOnMissingBean(ClientInitializer.class)
public class MockHttpClient implements MockClient, ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private CompileManager compileManager;
    @Autowired
    private MockConf mockConf;
    @Autowired
    private MockContextUtil mockContextUtil;

    private MockCompile mockCompile;


    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setConnectTimeout(20000)
            .setConnectionRequestTimeout(10000)
            .setSocketTimeout(10000)
            .build();

    private static final String URL = "/getAppId";

    @Override
    public String getAppId() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(mockConf.getHost() + ":" + mockConf.getPort() + mockConf.getPrefix() + URL);
            httpPost.setConfig(MockHttpClient.REQUEST_CONFIG);

            //设置消息头
            httpPost.addHeader("Content-type", "application/json;charset=utf-8");
            httpPost.setHeader("Accept", "application/json");

            MockReq req = new MockReq();
            req.setUuid(UUID.randomUUID().toString());
            req.setThreadId(Thread.currentThread().getId());
            req.setAppId(mockContextUtil.getAppId());
            req.setAppName(mockContextUtil.getAppName());
            req.setOpt(OptType.APPID);

            log.info("[{}]mockHttpClient req\r\n[{}]", URL, JSON.toJSONString(req, SerializerFeature.PrettyFormat));

            //设置消息体
            httpPost.setEntity(new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8")));

            CloseableHttpResponse response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                log.info("mockHttpClient error：{}", response.getStatusLine());
                throw new MockException("mockHttpClient 请求异常");
            }

            HttpEntity entity = response.getEntity();
            String res = EntityUtils.toString(entity);
            log.info("mockHttpClient res:{}", res);

            MockResp<String> resp = JSON.parseObject(res, new TypeReference<MockResp<String>>() {
            });

            if (!resp.isSuccess() || !StringUtils.hasLength(resp.getData())) {
                log.warn("获取项目appId失败:[{}]", resp.getMsg());
                System.exit(-1);
            }

            return resp.getData();
        } catch (Exception e) {
            log.error("mockHttpClient error：{}", e.getMessage(), e);
            System.exit(-1);
            return null;
        }
    }

    @Override
    public void onApplicationEvent(@Nullable ApplicationReadyEvent event) {
        mockCompile = compileManager.getSpiMap(mockConf.getCompile(), event);
    }

    @Override
    public <R> R doMock(Type type, String url, Map<String, Object> paras, boolean isRpc) {

        if (isRpc) {
            url = BeanMockUtil.getMockUrl(url);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(mockConf.getHost() + ":" + mockConf.getPort() + mockConf.getPrefix() + url);
            httpPost.setConfig(MockHttpClient.REQUEST_CONFIG);

            //设置消息头
            httpPost.addHeader("Content-type", "application/json;charset=utf-8");
            httpPost.setHeader("Accept", "application/json");

            MockReq req = new MockReq();
            req.setAppId(mockContextUtil.getAppId());
            req.setAppName(mockContextUtil.getAppName());
            req.setUuid(UUID.randomUUID().toString());
            req.setThreadId(Thread.currentThread().getId());
            req.setOpt(OptType.MOCK);
            req.setRoute(url);
            req.setData(mockCompile.decode(paras));

            log.info("[{}]mockHttpClient req\r\n[{}]", url, JSON.toJSONString(req, SerializerFeature.PrettyFormat));

            //设置消息体
            httpPost.setEntity(new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8")));

            CloseableHttpResponse response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                log.info("mockHttpClient error：{}", response.getStatusLine());
                throw new MockException("mockHttpClient 请求异常");
            }

            HttpEntity entity = response.getEntity();
            String res = EntityUtils.toString(entity);
            log.info("mockHttpClient res:{}", res);

            MockResp<String> resp = JSON.parseObject(res, new TypeReference<MockResp<String>>() {
            });

            if (!resp.isSuccess()) {
                throw new MockException(resp.getMsg());
            }

            return BeanMockUtil.resolveRes((String) mockCompile.encode(resp.getData()), type, resp.getDataClass());

        } catch (Exception e) {
            log.error("mockHttpClient error：{}", e.getMessage(), e);
            throw new MockException(e.getMessage());
        }

    }


}

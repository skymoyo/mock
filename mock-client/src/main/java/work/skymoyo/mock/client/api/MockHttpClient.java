package work.skymoyo.mock.client.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.stereotype.Component;
import work.skymoyo.mock.client.spi.CompileManager;
import work.skymoyo.mock.client.spi.MockCompile;
import work.skymoyo.mock.common.enums.OptType;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mork.rpc.config.MockConf;
import work.skymoyo.mork.rpc.netty.ClientInitializer;

import java.nio.charset.Charset;
import java.util.UUID;

@Slf4j
@Component
@ConditionalOnMissingBean(ClientInitializer.class)
public class MockHttpClient implements MockClient {

    @Autowired
    private CompileManager compileManager;
    @Autowired
    private MockConf mockConf;

    private MockCompile mockCompile;


    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setConnectTimeout(80000)
            .setConnectionRequestTimeout(10000)
            .setSocketTimeout(70000)
            .build();


    /**
     * 从这里直接获取，否者有NPE风险
     *
     * @return
     */
    private MockCompile getMockCompile() {
        return mockCompile == null ? this.mockCompile = compileManager.getSpiMap(mockConf.getCompile()) : mockCompile;
    }


    @Override
    public <R> R doMock(Class<R> returnClazz, String url, boolean isRpc, Object... paras) {

        if (isRpc) {
            url = getMockUrl(url);
        }


        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(mockConf.getHost() + ":" + mockConf.getPort() + mockConf.getPrefix() + url);
            httpPost.setConfig(MockHttpClient.REQUEST_CONFIG);

            if (httpPost != null && paras != null) {
                try {
                    //设置消息头
                    httpPost.addHeader("Content-type", "application/json;charset=utf-8");
                    httpPost.setHeader("Accept", "application/json");

                    MockReq<Object> req = new MockReq<>();
                    req.setUuid(UUID.randomUUID().toString());
                    req.setOpt(OptType.MOCK);
                    req.setRoute(url);
                    req.setData(this.getMockCompile().encode(paras));

                    log.info("[{}]发送post[{}]", url, JSON.toJSONString(req));

                    //设置消息体
                    httpPost.setEntity(new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8")));

                    long startTime = System.currentTimeMillis();
                    CloseableHttpResponse response = httpClient.execute(httpPost);
                    long endTime = System.currentTimeMillis();

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode != HttpStatus.SC_OK) {
                        log.info("接口执行失败：{}", response.getStatusLine());
                    }

                    String res = EntityUtils.toString(response.getEntity());

                    log.info("Post接口执行花费时间（单位：毫秒）：{} | " + "状态（0.成功 1.执行方法失败 2.协议错误 3.网络错误）：{} \r\nres:{}", (endTime - startTime), statusCode, res);
                    return this.resolveRes((String) this.getMockCompile().decode(res), returnClazz);

                } catch (Exception e) {
                    log.error("Post接口执行时错误:{}", e.getMessage(), e);
                    throw new RuntimeException("Post接口执行时错误 " + e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }


}

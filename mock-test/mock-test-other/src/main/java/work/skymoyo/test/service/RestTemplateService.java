package work.skymoyo.test.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class RestTemplateService {

    @Autowired
    private RestTemplate restTemplate;


    public <T> T rest(String url, String body, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("hello", "mock");
        HttpEntity httpEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<T> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);

        try {
            log.info("fastjson:{}", JSON.toJSONString(exchange));
            log.info("gson:{}", new Gson().toJson(exchange));
            log.info("jackson:{}", new ObjectMapper().writeValueAsString(exchange));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        if (!exchange.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException();
        }
        return exchange.getBody();
    }

    //todo
    public <T> T apacheHttp(String url, Class<T> responseType) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        //设置消息头
        httpGet.addHeader("Content-type", "application/json;charset=utf-8");
        httpGet.setHeader("Accept", "application/json");
        try {
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            org.apache.http.HttpEntity entity = execute.getEntity();

            log.info("fastjson:{}", JSON.toJSONString(entity));
            log.info("gson:{}", new Gson().toJson(entity));
            log.info("jackson:{}", new ObjectMapper().writeValueAsString(entity));

            String res = EntityUtils.toString(entity);

            return (T) res;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}

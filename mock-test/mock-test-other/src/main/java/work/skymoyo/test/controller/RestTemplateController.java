package work.skymoyo.test.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import work.skymoyo.test.service.RestTemplateService;

@Slf4j
@RestController
@RequestMapping("/rest")
public class RestTemplateController {

    @Autowired(required = false)
    private RestTemplateService restTemplateService;


    @GetMapping(value = "/test", produces = {"application/json"})
    public String save(@RequestParam("body") String body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("hello", "mock");
        HttpEntity<String> objectHttpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> rest = restTemplateService.rest("http://127.0.0.1:8080/test/say/123121", HttpMethod.GET, objectHttpEntity, String.class);

        try {
            log.info("fastjson:{}", JSON.toJSONString(rest));
            log.info("gson:{}", new Gson().toJson(rest));
            log.info("jackson:{}", new ObjectMapper().writeValueAsString(rest));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return rest.getBody();
    }


}

package work.skymoyo.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Object save(@RequestParam("body") String body) {
        restTemplateService.rest("http://127.0.0.1:8080/test/say/123121", body, String.class);
        restTemplateService.rest("http://127.0.0.1:8080/test/say/123121", body, String.class);
        return restTemplateService.rest("http://127.0.0.1:8080/test/say/123121", body, String.class);
//         restTemplateService.apacheHttp("http://127.0.0.1:8080/test/say/123121", String.class);
//        return null;


    }


}

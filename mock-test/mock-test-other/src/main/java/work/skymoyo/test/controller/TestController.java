package work.skymoyo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.skymoyo.mock.client.client.MockNettyClient;
import work.skymoyo.test.model.Person;
import work.skymoyo.test.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired(required = false)
    private MockNettyClient mockNettyClient;

    @Autowired
    private TestService testService;


    @GetMapping(value = "/say/{hello}", produces = {"application/json"})
    public String save(@PathVariable("hello") String hello, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String servletPath = httpServletRequest.getServletPath();
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("hello", hello);
        return mockNettyClient.doMock(String.class, servletPath, map, false);
    }


    @GetMapping(value = "/proxy/{hello}", produces = {"application/json"})
    public Object proxy(@PathVariable("hello") String hello) {
        return testService.helloSpi(hello);
    }


    @GetMapping(value = "/getPerson/{hello}", produces = {"application/json"})
    public List<Person> getPerson(@PathVariable("hello") String hello) {

        return testService.getPersonByAes(hello);

    }

    @GetMapping(value = "/getPerson", produces = {"application/json"})
    public Person getPerson() {
        return testService.getPerson();
    }


}

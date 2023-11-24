package work.skymoyo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.skymoyo.mock.client.client.MockNettyClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/mock")
public class MockController {

    @Autowired(required = false)
    private MockNettyClient mockNettyClient;

    @GetMapping(value = "/say/{hello}", produces = {"application/json"})
    public String save(@PathVariable("hello") String hello, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String servletPath = httpServletRequest.getServletPath();
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("hello", hello);
        return mockNettyClient.doMock(String.class, servletPath, map, false);
    }


}

package work.skymoyo.test.controller;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.skymoyo.mock.client.client.MockNettyClient;
import work.skymoyo.test.model.Person;
import work.skymoyo.test.service.TestService;
import work.skymoyo.test.utils.AesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return mockNettyClient.doMock(String.class, servletPath, false, hello);
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


    public static void main(String[] args) {

//表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        Object object = new Object();
        EvaluationContext ctx = new StandardEvaluationContext(object);
        Rule rule = new Rule();
        rule.setId(1L);
        rule.setName("夫");
        ArrayList<Rule> rules = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            Rule child = new Rule();
            child.setId((long) i);
            child.setName("c" + i);
            rules.add(child);
        }
        rule.setChilds(rules);

        Map<Object, Object> map = new HashMap<>();
        map.put("map", "maptest");


        Object[] datas = {1L, rule, map};

        System.out.println(JSON.toJSONString(datas));
        ctx.setVariable("datas", datas);
        Object value = parser.parseExpression("#datas[2][map]").getValue(ctx);
        System.out.println(value);

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Rule {
        private Long id;
        private String name;
        private List<Rule> childs;

    }

}

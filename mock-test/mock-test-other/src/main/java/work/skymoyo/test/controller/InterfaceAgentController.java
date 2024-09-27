package work.skymoyo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.skymoyo.test.resource.dao.MockRuleDao;
import work.skymoyo.test.service.TestInterface;

@RestController
@RequestMapping("/interfaceAgent")
public class InterfaceAgentController {

    @Autowired(required = false)
    private MockRuleDao mockRuleDao;
    @Autowired(required = false)
    private TestInterface testInterface;

    @GetMapping(value = "/test", produces = {"application/json"})
    public Object test() {
        return testInterface.test();
    }

    @GetMapping(value = "/defaultTest", produces = {"application/json"})
    public Object defaultTest() {
        return testInterface.defaultTest("");
    }

    @GetMapping(value = "/queryResultById", produces = {"application/json"})
    public Object queryResultById() {
        return mockRuleDao.queryResultById(1L);
    }
}

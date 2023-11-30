package work.skymoyo.mock.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mock.core.service.MockService;
import work.skymoyo.mock.core.service.OptService;
import work.skymoyo.mock.core.service.rule.MockHandleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping
public class MockController {

    @Autowired
    private MockService mockService;
    @Autowired
    private MockHandleManager mockHandleManager;


    @RequestMapping(value = "/allReq/**", method = {RequestMethod.GET, RequestMethod.POST})
    public MockResp<String> mock(@RequestBody MockReq req) {
        OptService optService = mockHandleManager.selectorHandle(MockHandleTypeEnum.REQ, req.getOpt().name(), OptService.class);
        return optService.exec(req);
    }

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST})
    public Object mock(HttpServletRequest request, HttpServletResponse response) {
        return mockService.mockHttp(request, response);
    }


}

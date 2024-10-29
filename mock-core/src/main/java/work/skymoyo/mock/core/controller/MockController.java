package work.skymoyo.mock.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mock.core.resource.dao.MockRecordDao;
import work.skymoyo.mock.core.service.MockService;
import work.skymoyo.mock.core.service.OptService;
import work.skymoyo.mock.core.service.rule.MockHandleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping
public class MockController {

    @Autowired
    private MockRecordDao mockRecordDao;
    @Autowired
    private MockService mockService;
    @Autowired
    private MockHandleManager mockHandleManager;


    @RequestMapping(value = "/allReq/**")
    public MockResp<String> mock(@RequestBody MockReq req) {
        try {
            OptService optService = mockHandleManager.selectorHandle(MockHandleTypeEnum.REQ, req.getOpt().name(), OptService.class);
            return optService.exec(req);
        } catch (Exception e) {
            MockResp exec = new MockResp();
            exec.setSuccess(false);
            exec.setUuid(req.getUuid());
            exec.setMsg(e.getMessage());
            return exec;
        }
    }

    @RequestMapping(value = "/agent/**")
    public Object mock(HttpServletRequest request, HttpServletResponse response) {
        return mockService.mockHttp(request, response);
    }


}

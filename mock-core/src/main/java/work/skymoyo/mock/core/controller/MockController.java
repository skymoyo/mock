package work.skymoyo.mock.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.enums.OptType;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mock.core.resource.dao.MockRecordDao;
import work.skymoyo.mock.core.resource.entity.MockRecord;
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


    @RequestMapping(value = "/allReq/**", method = {RequestMethod.GET, RequestMethod.POST})
    public MockResp<String> mock(@RequestBody MockReq req) {
        MockRecord record = new MockRecord();
        record.setAppId(req.getAppId());
        record.setAppName(req.getAppName());
        record.setClient(req.getClient());
        record.setThreadId(req.getThreadId());
        record.setUuid(req.getUuid());
        record.setRoute(req.getRoute());
        record.setMockReq(req.toString());
        MockResp exec = null;
        try {
            mockRecordDao.insert(record);
            OptService optService = mockHandleManager.selectorHandle(MockHandleTypeEnum.REQ, req.getOpt().name(), OptService.class);
            exec = optService.exec(req);

            if (req.getOpt() == OptType.APPID) {
                record.setAppId((String) exec.getData());
            }

        } catch (Exception e) {
            exec = new MockResp();
            exec.setSuccess(false);
            exec.setUuid(req.getUuid());
            exec.setMsg(e.getMessage());
        } finally {
            if (record.getId() != null) {
                record.setMockResp(exec.toString());
                mockRecordDao.update(record);
            }
        }


        return exec;
    }

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST})
    public Object mock(HttpServletRequest request, HttpServletResponse response) {
        return mockService.mockHttp(request, response);
    }


}

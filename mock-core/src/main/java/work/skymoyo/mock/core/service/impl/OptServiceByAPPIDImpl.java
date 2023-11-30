package work.skymoyo.mock.core.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mock.core.annotation.MockHandle;
import work.skymoyo.mock.core.resource.dao.MockRecordDao;
import work.skymoyo.mock.core.resource.entity.MockRecord;
import work.skymoyo.mock.core.service.OptService;

import java.util.UUID;

@Slf4j
@MockHandle(type = MockHandleTypeEnum.REQ, method = "APPID")
public class OptServiceByAPPIDImpl implements OptService<String> {
    @Autowired
    private MockRecordDao mockRecordDao;

    @Override
    public MockResp<String> exec(MockReq req) {
        MockResp<String> resp = new MockResp<>();
        resp.setUuid(req.getUuid());

        try {
            resp.setData(UUID.randomUUID().toString().replace("-", ""));
            resp.setSuccess(true);
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setMsg(e.getMessage());
        } finally {
            MockRecord record = new MockRecord();
            record.setAppId(resp.getData());
            record.setAppName(req.getAppName());
            record.setMockReq(req.toString());
            record.setThreadId(req.getThreadId());
            record.setMockResp(resp.toString());
            mockRecordDao.insert(record);
        }
        return resp;
    }
}

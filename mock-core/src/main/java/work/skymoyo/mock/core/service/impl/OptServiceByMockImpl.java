package work.skymoyo.mock.core.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mock.core.annotation.MockHandle;
import work.skymoyo.mock.core.resource.dao.MockRecordDao;
import work.skymoyo.mock.core.resource.entity.MockRecord;
import work.skymoyo.mock.core.service.MockService;
import work.skymoyo.mock.core.service.OptService;


@Slf4j
@MockHandle(type = MockHandleTypeEnum.REQ, method = "MOCK")
public class OptServiceByMockImpl implements OptService<String> {

    @Autowired
    private MockService mockService;
    @Autowired
    private MockRecordDao mockRecordDao;

    @Override
    public MockResp<String> exec(MockReq req) {

        MockRecord record = new MockRecord();
        record.setAppId(req.getAppId());
        record.setAppName(req.getAppName());
        record.setMockReq(req.toString());
        record.setThreadId(req.getThreadId());

        MockResp<String> resp = new MockResp<>();
        resp.setUuid(req.getUuid());
        try {
            mockRecordDao.insert(record);

            MockDataBo bo = mockService.mock(req);
            resp.setDataClass(bo.getDataClass());
            resp.setData(bo.getData());
            resp.setSuccess(true);
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setMsg(e.getMessage());
        } finally {
            if(record.getId()!=null){
                record.setMockResp(resp.toString());
                mockRecordDao.update(record);
            }
        }
        return resp;
    }
}

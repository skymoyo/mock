package work.skymoyo.mock.core.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mock.core.service.MockService;
import work.skymoyo.mock.core.service.OptService;

@Slf4j
@Service
public class OptServiceByMockImpl implements OptService<String> {

    @Autowired
    private MockService mockService;


    @Override
    public MockResp<String> exec(MockReq req) {

        MockResp<String> resp = new MockResp<>();
        resp.setUuid(req.getUuid());

        try {
            MockDataBo bo = mockService.mock(req);
            resp.setDataClass(bo.getDataClass());
            resp.setData(bo.getData());
            resp.setSuccess(true);
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setMsg(e.getMessage());
        }
        return resp;
    }
}

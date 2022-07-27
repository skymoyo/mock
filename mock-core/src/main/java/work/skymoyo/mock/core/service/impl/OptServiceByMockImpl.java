package work.skymoyo.mock.core.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mock.core.service.MockService;
import work.skymoyo.mock.core.service.OptService;

@Slf4j
@Service
public class OptServiceByMockImpl implements OptService<Object, String> {

    @Autowired
    private MockService mockService;


    @Override
    public MockResp<String> exec(MockReq<Object> req) {

        MockResp resp = new MockResp<>();
        resp.setUuid(req.getUuid());
        resp.setData(mockService.mock(req));
        return resp;
    }
}

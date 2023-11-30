package work.skymoyo.mock.core.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mock.core.annotation.MockHandle;
import work.skymoyo.mock.core.service.OptService;

@Slf4j
@MockHandle(type = MockHandleTypeEnum.REQ, method = "APPID")
public class OptServiceByAPPIDImpl implements OptService<Boolean > {

    @Override
    public MockResp<Boolean> exec(MockReq req) {

        MockResp<Boolean> resp = new MockResp<>();
        resp.setUuid(req.getUuid());
        try {
            String appId = req.getAppId();

            resp.setData(StringUtils.hasLength(appId));
            resp.setSuccess(true);
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setMsg(e.getMessage());
        }
        return resp;
    }
}

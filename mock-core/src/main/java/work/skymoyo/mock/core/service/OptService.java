package work.skymoyo.mock.core.service;

import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;

public interface OptService<T> extends MockHandleInterface {

    MockResp<T> exec(MockReq mockReq);


}

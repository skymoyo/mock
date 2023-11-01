package work.skymoyo.mock.core.service.rule;

import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.resource.entity.MockRule;
import work.skymoyo.mock.core.service.MockHandleInterface;

public interface MockResultService extends MockHandleInterface {

    String getResult(MockReq req, MockRule mockRule);

}

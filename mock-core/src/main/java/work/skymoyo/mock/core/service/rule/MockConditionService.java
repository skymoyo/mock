package work.skymoyo.mock.core.service.rule;


import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.resource.entity.MockCondition;
import work.skymoyo.mock.core.service.MockHandleInterface;

public interface MockConditionService  extends MockHandleInterface {

    Boolean mockConditionValue(MockReq req, MockCondition f);
}

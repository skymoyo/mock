package work.skymoyo.mock.core.service.rule;


import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.resource.entity.MockCondition;

public interface MockConditionService<Q> {

    Object mockConditionValue(MockReq<Q> req, MockCondition f);
}

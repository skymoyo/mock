package work.skymoyo.mock.core.service.rule;

import work.skymoyo.mock.common.enums.GetConditionValueType;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.annotation.GetConditionValue;
import work.skymoyo.mock.core.resource.entity.MockCondition;

@GetConditionValue(GetConditionValueType.ROUTE)
public class GetConditionValueByRoute implements MockConditionService<Object> {

    @Override
    public String mockConditionValue(MockReq<Object> req, MockCondition f) {
        return req.getRoute().replace("mock", "").replace("/mock", "");
    }

}

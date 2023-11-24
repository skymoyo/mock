package work.skymoyo.mock.core.service.rule;

import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.utils.SpelUtil;
import work.skymoyo.mock.core.annotation.MockHandle;
import work.skymoyo.mock.core.resource.entity.MockCondition;

@MockHandle(type = MockHandleTypeEnum.REQ, method = "params")
public class GetConditionValueByParams implements MockConditionService {

    public Boolean mockConditionValue(MockReq req, MockCondition mockCondition) {
        return SpelUtil.compileParams(req.getData(), mockCondition.getConditionKey(), Boolean.class);
    }
}

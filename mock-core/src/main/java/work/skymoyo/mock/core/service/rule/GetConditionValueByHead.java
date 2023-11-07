package work.skymoyo.mock.core.service.rule;

import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.utils.SpelUtil;
import work.skymoyo.mock.core.annotation.MockHandle;
import work.skymoyo.mock.core.resource.entity.MockCondition;


@MockHandle(type = MockHandleTypeEnum.REQ, method = "head")
public class GetConditionValueByHead implements MockConditionService {

    public Boolean mockConditionValue(MockReq req, MockCondition mockCondition) {
        return SpelUtil.compileParams(req.getHead(), mockCondition.getConditionKey(), Boolean.class);
    }
}

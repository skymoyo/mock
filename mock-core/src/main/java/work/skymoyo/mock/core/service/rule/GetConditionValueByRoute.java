package work.skymoyo.mock.core.service.rule;

import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.utils.SpelUtil;
import work.skymoyo.mock.core.annotation.MockHandle;
import work.skymoyo.mock.core.resource.entity.MockCondition;

import java.util.HashMap;

@MockHandle(type = MockHandleTypeEnum.REQ, method = "route")
public class GetConditionValueByRoute implements MockConditionService {

    @Override
    public Boolean mockConditionValue(MockReq req, MockCondition f) {
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("route", req.getRoute());
        return SpelUtil.compileParams(map, f.getConditionKey(), Boolean.class);
    }

}

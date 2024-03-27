package work.skymoyo.mock.core.admin.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import work.skymoyo.mock.core.resource.entity.MockCondition;
import work.skymoyo.mock.core.resource.entity.MockRule;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MockRuleVO extends MockRule {

    private List<MockCondition> conditionList;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

package work.skymoyo.mock.core.admin.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import work.skymoyo.mock.core.resource.entity.MockConfig;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MockConfigVO extends MockConfig {

    List<MockRuleVO> ruleList;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

package work.skymoyo.mock.core.service.rule;

import org.springframework.beans.factory.annotation.Autowired;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.annotation.MockHandle;
import work.skymoyo.mock.core.resource.dao.MockRuleDao;
import work.skymoyo.mock.core.resource.entity.MockRule;


@MockHandle(type = MockHandleTypeEnum.RESP, method = "10")
public class MockResultByDefService implements MockResultService {


    @Autowired
    private MockRuleDao mockRuleDao;


    @Override
    public MockDataBo getResult(MockReq req, MockRule mockRule) {
        return mockRuleDao.queryResultById(mockRule.getId());
    }


}

package work.skymoyo.mock.core.service.rule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.resource.dao.MockRuleDao;
import work.skymoyo.mock.core.resource.entity.MockRule;


@Slf4j
@Service
public class MockResultByBeanService implements MockResultService {


    @Autowired
    private MockRuleDao mockRuleDao;

    @Override
    public MockDataBo getResult(MockReq req, MockRule mockRule) {
        log.info("MockResultByBeanService 执行结果处理");
        return mockRuleDao.queryResultById(mockRule.getId());
    }


}

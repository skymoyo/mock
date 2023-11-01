package work.skymoyo.mock.core.service.rule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.spi.Spi;
import work.skymoyo.mock.core.resource.dao.MockRuleDao;
import work.skymoyo.mock.core.resource.entity.MockRule;


/**
 * alter table mock_rule modify rule_type varchar(8) not null comment '结果生成类型 10 直接返回 20 动态生成';
 */

@Spi("RESP_SPI")
@Slf4j
@Service
public class MockResultBySPIService implements MockResultService {


    @Autowired
    private MockRuleDao mockRuleDao;

    @Override
    public String getResult(MockReq req, MockRule mockRule) {
        log.info("SPI 执行结果处理");
        return mockRuleDao.queryResultById(mockRule.getId());
    }


}
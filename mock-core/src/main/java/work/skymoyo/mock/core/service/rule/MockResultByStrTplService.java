package work.skymoyo.mock.core.service.rule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.utils.SpelUtil;
import work.skymoyo.mock.core.annotation.MockHandle;
import work.skymoyo.mock.core.resource.dao.MockRuleDao;
import work.skymoyo.mock.core.resource.entity.MockRule;


@Slf4j
@MockHandle(type = MockHandleTypeEnum.RESP, method = "spel")
public class MockResultByStrTplService implements MockResultService {


    @Autowired
    private MockRuleDao mockRuleDao;

    @Override
    public String getResult(MockReq req, MockRule mockRule) {
        log.info("MockResultByStrTplService 执行结果处理开始");
        String spel = mockRuleDao.queryResultById(mockRule.getId());

        if (!spel.startsWith("'")) {
            spel = "'" + spel;
        }
        if (!spel.endsWith("'")) {
            spel = spel + "'";
        }

        return SpelUtil.compileParams(req.getData(), spel, String.class);
    }

}

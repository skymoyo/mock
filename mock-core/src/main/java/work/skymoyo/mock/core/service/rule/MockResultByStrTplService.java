package work.skymoyo.mock.core.service.rule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.annotation.MockHandle;
import work.skymoyo.mock.core.resource.dao.MockRuleDao;
import work.skymoyo.mock.core.resource.entity.MockRule;


@Slf4j
@MockHandle(type = MockHandleTypeEnum.RESP, method = "spel")
public class MockResultByStrTplService implements MockResultService {

    /**
     * spel表达式解析器
     */
    private static SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

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

        EvaluationContext context = new StandardEvaluationContext();
        ((StandardEvaluationContext) context).setVariables(req.getData());

        return spelExpressionParser.parseExpression(spel).getValue(context,String.class);

    }

}

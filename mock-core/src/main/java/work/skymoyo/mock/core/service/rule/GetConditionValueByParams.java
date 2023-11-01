package work.skymoyo.mock.core.service.rule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import work.skymoyo.mock.common.enums.GetConditionValueType;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.annotation.GetConditionValue;
import work.skymoyo.mock.core.resource.entity.MockCondition;


/**
 * 根据EL
 */
@Slf4j
@GetConditionValue(GetConditionValueType.PARAMS)
public class GetConditionValueByParams implements MockConditionService {

    /**
     * spel表达式解析器
     */
    private static SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    public String mockConditionValue(MockReq req, MockCondition mockCondition) {

        Object object = new Object();
        EvaluationContext ctx = new StandardEvaluationContext(object);
        ((StandardEvaluationContext) ctx).setVariables(req.getData());
        try {
            return spelExpressionParser.parseExpression(mockCondition.getConditionKey()).getValue(ctx, String.class);
        } catch (EvaluationException e) {
            log.warn("解析异常[{}]", e.getMessage(), e);
            return null;
        }
    }
}

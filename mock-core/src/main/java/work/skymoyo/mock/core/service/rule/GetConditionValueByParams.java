package work.skymoyo.mock.core.service.rule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ExpressionParser;
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
public class GetConditionValueByParams implements MockConditionService<Object> {


    public String mockConditionValue(MockReq<Object> req, MockCondition mockCondition) {

        ExpressionParser parser = new SpelExpressionParser();
        Object object = new Object();
        EvaluationContext ctx = new StandardEvaluationContext(object);
        ctx.setVariable("datas", req.getData());
        try {
            return parser.parseExpression("#datas" + mockCondition.getConditionKey()).getValue(ctx, String.class);
        } catch (EvaluationException e) {
            log.warn("解析异常[{}]", e.getMessage(), e);
            return null;
        }
    }
}

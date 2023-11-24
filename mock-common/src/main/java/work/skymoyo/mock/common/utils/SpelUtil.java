package work.skymoyo.mock.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Slf4j
public class SpelUtil {


    /**
     * spel表达式解析器
     */
    private static SpelExpressionParser spelExpressionParser = new SpelExpressionParser();


    public static <T> T compileParams(Map<String, Object> map, String spel, Class<T> clazz) {

        try {

            EvaluationContext context = new StandardEvaluationContext();
            if (!CollectionUtils.isEmpty(map)) {
                ((StandardEvaluationContext) context).setVariables(map);
            }

            return spelExpressionParser.parseExpression(spel).getValue(context, clazz);
        } catch (Exception e) {
            log.error("参数解析异常spel:[{}] , 上下文: [{}] 返回类型: [{}]", spel, map, clazz);
        }

        return null;

    }
}

package work.skymoyo.mock.core.service.rule;

import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mock.common.enums.GetConditionValueType;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import work.skymoyo.mock.core.annotation.GetConditionValue;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Deprecated
public class GetConditionValueManager implements ApplicationListener<ApplicationReadyEvent> {

    private final Map<String, Object> getConditionBeanMap = new HashMap<>(GetConditionValueType.values().length);


    /**
     * 根据类型和接口拿bean
     *
     * @param type
     * @return bean or null
     */
    public <T> T selectorGetCondition(String type) {
        return (T) getConditionBeanMap.get(type.toUpperCase());

    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, Object> processors = event.getApplicationContext().getBeansWithAnnotation(GetConditionValue.class);

        if (CollectionUtils.isEmpty(processors)) {
            return;
        }

        processors.forEach((key, value) -> {
            Class<?> clazz = value.getClass();
            Class<?>[] interfaces = clazz.getInterfaces();
            GetConditionValue annotation = clazz.getAnnotation(GetConditionValue.class);
            GetConditionValue.GetConditionValues annotations = clazz.getAnnotation(GetConditionValue.GetConditionValues.class);

            //存在被代理的情况
            if (annotation == null && annotations == null) {
                Class<?> realClass = clazz.getSuperclass();
                interfaces = realClass.getInterfaces();

                annotation = realClass.getDeclaredAnnotation(GetConditionValue.class);
                annotations = realClass.getDeclaredAnnotation(GetConditionValue.GetConditionValues.class);
            }

            if (annotation != null) {
                getConditionBeanMap.put(annotation.value().name(), value);
            } else {
                if (annotations != null && annotations.value().length > 0) {
                    for (GetConditionValue getConditionValue : annotations.value()) {
                        getConditionBeanMap.put(getConditionValue.value().name(), value);
                    }
                }
            }
        });

        getConditionBeanMap.forEach((k, v) -> {
            log.info("getConditionBeanMap >>>>>> {}:{}", k, v);
        });
    }


}
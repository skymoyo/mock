package work.skymoyo.mock.core.annotation;


import work.skymoyo.mock.common.enums.GetConditionValueType;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;


@Service
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface GetConditionValue {


    GetConditionValueType value();


    @Service
    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface GetConditionValues {
        GetConditionValue[] value();
    }
}

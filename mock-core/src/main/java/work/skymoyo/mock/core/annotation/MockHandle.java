package work.skymoyo.mock.core.annotation;


import org.springframework.stereotype.Service;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;

import java.lang.annotation.*;


@Service
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MockHandle {


    MockHandleTypeEnum type();


    String method();


    @Service
    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface MockHandles {
        MockHandle[] value();
    }
}

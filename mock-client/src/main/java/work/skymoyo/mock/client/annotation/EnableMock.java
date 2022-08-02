package work.skymoyo.mock.client.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import work.skymoyo.mock.client.config.AutoConfig;

import java.lang.annotation.*;


@Service
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(AutoConfig.class)
public @interface EnableMock {
}

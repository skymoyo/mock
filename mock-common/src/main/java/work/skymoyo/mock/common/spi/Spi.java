package work.skymoyo.mock.common.spi;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Spi {

    String value() default "";
}

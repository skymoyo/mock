package work.skymoyo.mock.common.spi;


import java.lang.annotation.*;

@Repeatable(Spi.Spis.class)
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Spi {

    String value() default "";


    @Documented
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Spis {
        Spi[] value();
    }

}

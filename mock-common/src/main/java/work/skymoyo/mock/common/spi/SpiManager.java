package work.skymoyo.mock.common.spi;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class SpiManager {

    public static <T> Map<String, T> getSpiMap(Class<T> clazz) {

        return StreamSupport.stream(ServiceLoader.load(clazz).spliterator(), false)
                .map(n -> {
                    Class<?> implClass = n.getClass();

                    return Arrays.stream(Optional.ofNullable(implClass.getAnnotation(Spi.Spis.class))
                            .map(Spi.Spis::value)
                            .orElse(new Spi[]{implClass.getAnnotation(Spi.class)}))
                            .map(spi -> {
                                String value = spi.value();
                                return StringUtils.hasLength(value) ? value : implClass.getSimpleName();
                            })
                            .collect(Collectors.toMap(s -> s, s -> n));
                }).map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
}

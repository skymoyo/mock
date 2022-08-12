package work.skymoyo.mock.common.spi;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Optional;
import java.util.ServiceLoader;

public final class SpiManager {

    public static <T> HashMap<String, T> getSpiMap(Class<T> clazz) {
        HashMap<String, T> spiMap = new HashMap<>();
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);

        for (T next : serviceLoader) {
            Class<?> implClass = next.getClass();

            String key = Optional.ofNullable(implClass.getAnnotation(Spi.class))
                    .map(Spi::value)
                    .map(k -> StringUtils.hasLength(k) ? k : null)
                    .orElse(implClass.getSimpleName());

            spiMap.put(key, next);
        }

        return spiMap;
    }
}

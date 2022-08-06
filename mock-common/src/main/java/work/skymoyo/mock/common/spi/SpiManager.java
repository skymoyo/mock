package work.skymoyo.mock.common.spi;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.ServiceLoader;

public final class SpiManager {

    public static <T> HashMap<String, T> getSpiMap(Class<T> clazz) {
        HashMap<String, T> spiMap = new HashMap<>();
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);

        for (T next : serviceLoader) {
            Class<?> implClass = next.getClass();
            Spi annotation = implClass.getAnnotation(Spi.class);
            if (annotation != null) {
                String key = annotation.value();
                spiMap.put(ObjectUtils.isEmpty(key) ? implClass.getSimpleName() : key, next);
            }
        }

        return spiMap;
    }
}

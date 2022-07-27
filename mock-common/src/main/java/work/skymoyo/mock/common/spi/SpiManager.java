package work.skymoyo.mock.common.spi;

import java.util.HashMap;
import java.util.ServiceLoader;

public final class SpiManager {

    public static <T> HashMap<String, T> getSpiMap(Class<T> clazz) {
        HashMap<String, T> spiMap = new HashMap<>();
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);

        for (T next : serviceLoader) {
            Spi annotation = next.getClass().getAnnotation(Spi.class);
            if (annotation != null) {
                spiMap.put(annotation.value(), next);
            }
        }

        return spiMap;
    }
}

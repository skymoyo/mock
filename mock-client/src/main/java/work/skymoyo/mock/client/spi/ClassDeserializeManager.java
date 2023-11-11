package work.skymoyo.mock.client.spi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.common.spi.Spi;
import work.skymoyo.mock.common.spi.SpiManager;

import java.util.Map;

@Slf4j
@Component
public class ClassDeserializeManager {

    private volatile static Map<String, AbstractClassDeserialize> SPI_MAP;

    public static void init(ApplicationReadyEvent event) {

        if (SPI_MAP == null) {
            synchronized (ClassDeserializeManager.class) {
                if (SPI_MAP == null) {
                    SPI_MAP = SpiManager.getSpiMap(AbstractClassDeserialize.class);
                    Map<String, Object> processors = event.getApplicationContext().getBeansWithAnnotation(Spi.class);
                    processors.forEach((k, v) -> {
                        if (v instanceof AbstractClassDeserialize) {
                            SPI_MAP.put(k, (AbstractClassDeserialize) v);
                        }
                    });
                }
            }
            log.debug("客户端解析器spi加载：{}", SPI_MAP);
        }

    }

    public static AbstractClassDeserialize ofClass(Class clazz) {
        return ClassDeserializeManager.getSpiMap(clazz.getName());
    }

    public static AbstractClassDeserialize getSpiMap(String key) {
        return SPI_MAP.get(key);
    }

}

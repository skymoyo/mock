package work.skymoyo.mock.client.spi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.common.spi.Spi;
import work.skymoyo.mock.common.spi.SpiManager;

import java.util.Map;

@Slf4j
@Component
public class CompileManager {

    private volatile Map<String, MockCompile> spiMap;

    public MockCompile getSpiMap(String key, ApplicationReadyEvent event) {

        if (spiMap == null) {
            synchronized (CompileManager.class) {
                if (spiMap == null) {
                    spiMap = SpiManager.getSpiMap(MockCompile.class);
                    Map<String, Object> processors = event.getApplicationContext().getBeansWithAnnotation(Spi.class);
                    processors.forEach((k, v) -> {
                        if (v instanceof MockCompile) {
                            spiMap.put(k, (MockCompile) v);
                        }
                    });
                }
            }
        }

        log.debug("客户端编解码spi加载：{}", spiMap);
        return spiMap.get(key);
    }

}

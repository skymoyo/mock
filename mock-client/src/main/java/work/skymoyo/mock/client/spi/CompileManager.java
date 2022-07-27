package work.skymoyo.mock.client.spi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.common.spi.Spi;
import work.skymoyo.mock.common.spi.SpiManager;

import java.util.Map;

@Slf4j
@Component
public class CompileManager implements ApplicationListener<ApplicationReadyEvent> {


    private Map<String, MockCompile> spiMap;

    public MockCompile getSpiMap(String key) {
        return spiMap.get(key);
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        //如同名，默认优先
        spiMap = SpiManager.getSpiMap(MockCompile.class);

        Map<String, Object> processors = event.getApplicationContext().getBeansWithAnnotation(Spi.class);
        processors.forEach((k, v) -> {
            if (v instanceof MockCompile) {
                spiMap.put(k, (MockCompile) v);
            }
        });


        log.debug("客户端编解码spi加载：{}", spiMap);
    }

}

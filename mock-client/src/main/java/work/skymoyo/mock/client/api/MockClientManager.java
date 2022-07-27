package work.skymoyo.mock.client.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import work.skymoyo.mork.rpc.config.MockConf;

import java.util.Map;


@Slf4j
@Component
public class MockClientManager implements ApplicationListener<ApplicationReadyEvent> {

    private static MockConf mockConf;

    //todo 后续可能支持多个客户端
    private static Map<String, MockClient> MOCK_CLIENT_MAP;


    public static MockClient getOne() {
        return MOCK_CLIENT_MAP.get(mockConf.getRpc());
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        mockConf = applicationContext.getBean(MockConf.class);

        MOCK_CLIENT_MAP = applicationContext.getBeansOfType(MockClient.class);

        log.debug("客户端通讯处理加载：{}", MOCK_CLIENT_MAP);
    }

}

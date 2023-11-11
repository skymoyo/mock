package work.skymoyo.mock.client.client;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.client.spi.ClassDeserializeManager;

@Component
public class MockClientInit implements ApplicationListener<ApplicationReadyEvent> {


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ClassDeserializeManager.init(event);
    }
}

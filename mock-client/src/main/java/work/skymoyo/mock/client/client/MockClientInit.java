package work.skymoyo.mock.client.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.client.spi.ClassDeserializeManager;

@Component
public class MockClientInit implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private MockClient mockClient;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        mockClient.checkAppId();
        ClassDeserializeManager.init(event);
    }
}

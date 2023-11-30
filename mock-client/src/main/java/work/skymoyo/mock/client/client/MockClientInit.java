package work.skymoyo.mock.client.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.client.spi.ClassDeserializeManager;
import work.skymoyo.mock.client.utils.MockContextUtil;

@Component
public class MockClientInit implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private MockClient mockClient;
    @Autowired
    private MockContextUtil mockContextUtil;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        mockContextUtil.setAppId(mockClient.getAppId());
        ClassDeserializeManager.init(event);
    }
}

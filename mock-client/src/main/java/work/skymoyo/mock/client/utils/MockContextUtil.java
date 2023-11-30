package work.skymoyo.mock.client.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.rpc.config.MockConf;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MockContextUtil {

    private static MockConf innerMockConf;

    @Autowired
    private MockConf mockConf;

    @Value("${spring.application.name}")
    private String appName;

    private String appId;

    @PostConstruct
    private void init() {
        innerMockConf = mockConf;
    }

    public static boolean isEnableMock() {
        boolean enable = innerMockConf.isEnable();
        log.debug("method proxy enable mock :{}", enable);
        return enable;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}

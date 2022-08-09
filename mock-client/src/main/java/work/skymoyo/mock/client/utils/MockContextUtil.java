package work.skymoyo.mock.client.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.rpc.config.MockConf;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MockContextUtil {


    @Autowired
    private MockConf mockConf;

    private static MockConf innerMockConf;


    @PostConstruct
    private void init() {
        innerMockConf = mockConf;
    }

    public static boolean isEnableMock() {
        boolean enable = innerMockConf.isEnable();
        log.debug("method proxy enable mock :{}", enable);
        return enable;
    }


}

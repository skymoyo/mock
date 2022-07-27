package mock.test.dubbo.cusmer;


import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import lombok.SneakyThrows;
import mock.test.dubbo.provider.service.TestModel;
import mock.test.dubbo.provider.service.TestService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@SpringBootTest(classes = DubboTestApplication.class)
public class TestServiceTest {

    private static Logger logger = LoggerFactory.getLogger(TestServiceTest.class);

    @Autowired(required = false)
    private TestService testService;

    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private RegistryConfig registryConfig;

    @SneakyThrows
    @Test
    public void helloMock() {

        TestService first = this.getService("", TestService.class);
        logger.info("返回String：{}", first.helloString("foo"));
        logger.info("返回Integer：{}", first.helloInteger(18));
        logger.info("返回int：{}", first.helloInt(1));

        TestService second = this.getService("", TestService.class);
        logger.info("返回list：{}", second.helloList("mock list", 3));
        logger.info("返回map：{}", second.helloMap(2, new TestModel(1, "mock Map", "")));

        logger.info("返回Object：{}", this.testService.helloObject(2, "我是对象"));
        logger.info("重载返回Object：{}", this.testService.helloObject(2, "foo", "重载"));
        this.testService.helloVoid();
        logger.info("返回void：");

    }


    /**
     * 获取服务
     * 非线程安全
     *
     * @param group
     * @return
     * @throws
     */
    public <T> T getService(String group, Class<T> clazz) {

        ReferenceConfig<T> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setGroup(group);
        referenceConfig.setInterface(clazz);
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        T obj = cache.get(referenceConfig);
        if (obj == null) {
            cache.destroy(referenceConfig);
            referenceConfig = new ReferenceConfig<>();
            referenceConfig.setApplication(applicationConfig);
            referenceConfig.setRegistry(registryConfig);
            referenceConfig.setGroup(group);
            referenceConfig.setInterface(clazz);
            return cache.get(referenceConfig);
        } else {
            return obj;
        }
    }
}

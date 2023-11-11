package work.skymoyo.mock.core.service.rule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import work.skymoyo.mock.common.enums.MockHandleTypeEnum;
import work.skymoyo.mock.common.spi.Spi;
import work.skymoyo.mock.common.spi.SpiManager;
import work.skymoyo.mock.core.annotation.MockHandle;
import work.skymoyo.mock.core.service.MockHandleInterface;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
public class MockHandleManager implements ApplicationListener<ApplicationReadyEvent>, BeanFactoryAware {

    private final Map<String, Object> mockHandleBeanMap = new ConcurrentHashMap<>(16);

    private DefaultListableBeanFactory beanFactory;

    /**
     * 根据类型和接口拿bean
     *
     * @return bean or null
     */
    public <T extends MockHandleInterface> T selectorHandle(MockHandleTypeEnum handleType, String method, Class<? extends MockHandleInterface> clazz) {
        return (T) mockHandleBeanMap.computeIfAbsent(handleType.name() + "_" + method, key -> {
            //todo 可优化

            try {
                MockHandleInterface beanByName = beanFactory.getBean(method, clazz);
                return beanByName;
            } catch (Exception e) {
                //ignore
            }

            Map<String, ? extends MockHandleInterface> spiMap = SpiManager.getSpiMap(clazz);
            MockHandleInterface mockHandleInterface = spiMap.get(key);
            if (mockHandleInterface == null) {
                return null;
            }

            Class<? extends MockHandleInterface> aClass = mockHandleInterface.getClass();
            try {
                MockHandleInterface bean = beanFactory.getBean(aClass);
                return bean;
            } catch (Exception e) {
                //ignore
            }

            Spi annotation = aClass.getAnnotation(Spi.class);
            String spiInterfaceBeanName = Optional.of(annotation.value()).orElse(StringUtils.uncapitalize(aClass.getSimpleName()));

            boolean allMatch = Arrays.stream(aClass.getDeclaredFields())
                    .filter(field -> field.getAnnotation(Autowired.class) != null)
                    .map(field -> {
                        try {
                            String typeName = field.getGenericType().getTypeName();
                            field.setAccessible(true);
                            field.set(mockHandleInterface, beanFactory.getBean(Class.forName(typeName)));
                        } catch (Exception e) {
                            log.warn("注册新bean:[{}] beanName:[{}] field:[{}] Autowired 注入异常 ：{}", aClass, spiInterfaceBeanName, field.getName(), e.getMessage());
                            return false;
                        }
                        return true;
                    })
                    .allMatch(d -> d);

            if (!allMatch) {
                return null;
            }

            beanFactory.registerSingleton(spiInterfaceBeanName, mockHandleInterface);
            return beanFactory.getBean(spiInterfaceBeanName, clazz);

        });
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, Object> processors = event.getApplicationContext().getBeansWithAnnotation(MockHandle.class);

        if (CollectionUtils.isEmpty(processors)) {
            return;
        }

        processors.forEach((key, value) -> {
            Class<?> clazz = value.getClass();
            Class<?>[] interfaces = clazz.getInterfaces();
            MockHandle annotation = clazz.getAnnotation(MockHandle.class);
            MockHandle.MockHandles annotations = clazz.getAnnotation(MockHandle.MockHandles.class);

            //存在被代理的情况
            if (annotation == null && annotations == null) {
                Class<?> realClass = clazz.getSuperclass();
                interfaces = realClass.getInterfaces();

                annotation = realClass.getDeclaredAnnotation(MockHandle.class);
                annotations = realClass.getDeclaredAnnotation(MockHandle.MockHandles.class);
            }

            if (annotation != null) {
                mockHandleBeanMap.put(annotation.type() + "_" + annotation.method(), value);
            } else {
                if (annotations != null && annotations.value().length > 0) {
                    for (MockHandle mockHandle : annotations.value()) {
                        mockHandleBeanMap.put(mockHandle.type() + "_" + mockHandle.method(), value);
                    }
                }
            }
        });

        mockHandleBeanMap.forEach((k, v) -> {
            log.info("mockHandleBeanMap >>>>>> {}:{}", k, v);
        });
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

}

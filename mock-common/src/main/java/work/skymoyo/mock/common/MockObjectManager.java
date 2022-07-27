package work.skymoyo.mock.common;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import work.skymoyo.mock.common.model.BaseObject;

import javax.annotation.PostConstruct;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MockObjectManager {

    private Map<Byte, Class<? extends BaseObject>> MOCK_OBJECT_MAP;


    @PostConstruct
    private void init() {

        final Reflections reflections = new Reflections("work/skymoyo", BaseObject.class);
        Set<Class<? extends BaseObject>> mockObjectSet = reflections.getSubTypesOf(BaseObject.class);

        if (CollectionUtils.isEmpty(mockObjectSet)) {
            return;
        }


        MOCK_OBJECT_MAP = mockObjectSet.stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .collect(Collectors.toMap(c -> {
                            try {
                                return (Byte) c.getMethod("getObject").invoke(c.newInstance());
                            } catch (Exception e) {
                                log.error("BaseObject initializing occurs an error. ", e);
                            }
                            return null;
                        },
                        c -> c));

        log.debug("BaseObject init done:{}", MOCK_OBJECT_MAP);
    }


    public Class<? extends BaseObject> getObject(Byte b) {
        return MOCK_OBJECT_MAP.get(b);

    }
}

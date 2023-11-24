package work.skymoyo.mock.client.spi;

import org.springframework.util.StringUtils;

import java.lang.reflect.Type;


public abstract class AbstractClassDeserialize<T> {


    public T deserialize(String jsonStr, Type type, String dataClass) {
        if (!StringUtils.hasLength(jsonStr)) {
            return null;
        }

        return this.deserializer(jsonStr, type, dataClass);
    }

    public abstract T deserializer(String jsonStr, Type type, String dataClass);


}

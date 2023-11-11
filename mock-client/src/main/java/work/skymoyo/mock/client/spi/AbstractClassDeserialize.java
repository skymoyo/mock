package work.skymoyo.mock.client.spi;

import org.springframework.util.StringUtils;


public abstract class AbstractClassDeserialize<T> {


    public T deserialize(String jsonStr) {
        if (!StringUtils.hasLength(jsonStr)) {
            return null;
        }

        return this.deserializer(jsonStr);
    }

    public abstract T deserializer(String jsonStr);


}

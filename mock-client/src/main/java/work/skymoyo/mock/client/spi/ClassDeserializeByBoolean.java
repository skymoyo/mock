package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

import java.lang.reflect.Type;

@Spi("boolean")
@Spi("java.lang.Boolean")
public class ClassDeserializeByBoolean extends AbstractClassDeserialize<Boolean> {

    @Override
    public Boolean deserializer(String jsonStr, Type type, String dataClass) {
        return Boolean.valueOf(jsonStr);
    }


}

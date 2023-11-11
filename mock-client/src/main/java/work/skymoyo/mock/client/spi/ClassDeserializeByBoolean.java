package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

@Spi("boolean")
@Spi("java.lang.Boolean")
public class ClassDeserializeByBoolean extends AbstractClassDeserialize<Boolean> {

    @Override
    public Boolean deserializer(String jsonStr) {
        return Boolean.valueOf(jsonStr);
    }


}

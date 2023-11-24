package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

import java.lang.reflect.Type;

@Spi("void")
@Spi("java.lang.Void")
public class ClassDeserializeByVoid extends AbstractClassDeserialize<Void> {

    @Override
    public Void deserializer(String jsonStr, Type type, String dataClass) {
        return null;
    }


}

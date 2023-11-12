package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

import java.lang.reflect.Type;

@Spi("int")
@Spi("java.lang.Integer")
public class ClassDeserializeByInteger extends AbstractClassDeserialize<Integer> {

    @Override
    public Integer deserializer(String jsonStr, Type type, String dataClass) {
        return Integer.valueOf(jsonStr);
    }


}

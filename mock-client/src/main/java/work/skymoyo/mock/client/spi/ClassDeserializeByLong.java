package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

import java.lang.reflect.Type;

@Spi("long")
@Spi("java.lang.Long")
public class ClassDeserializeByLong extends AbstractClassDeserialize<Long> {

    @Override
    public Long deserializer(String jsonStr, Type type, String dataClass) {
        return Long.valueOf(jsonStr);
    }


}

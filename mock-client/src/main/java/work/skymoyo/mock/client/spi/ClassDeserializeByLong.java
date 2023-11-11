package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

@Spi("long")
@Spi("java.lang.Long")
public class ClassDeserializeByLong extends AbstractClassDeserialize<Long> {

    @Override
    public Long deserializer(String jsonStr) {
        return Long.valueOf(jsonStr);
    }


}

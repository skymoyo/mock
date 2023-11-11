package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

@Spi("int")
@Spi("java.lang.Integer")
public class ClassDeserializeByInteger extends AbstractClassDeserialize<Integer> {

    @Override
    public Integer deserializer(String jsonStr) {
        return Integer.valueOf(jsonStr);
    }


}

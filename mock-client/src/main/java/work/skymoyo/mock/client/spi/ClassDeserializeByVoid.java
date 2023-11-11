package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

@Spi("void")
@Spi("java.lang.Void")
public class ClassDeserializeByVoid extends AbstractClassDeserialize<Void> {

    @Override
    public Void deserializer(String jsonStr) {
        return null;
    }


}

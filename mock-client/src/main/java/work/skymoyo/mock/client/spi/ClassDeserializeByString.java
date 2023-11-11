package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

@Spi("java.lang.String")
public class ClassDeserializeByString extends AbstractClassDeserialize<String> {


    @Override
    public String deserializer(String jsonStr) {
        return jsonStr;
    }


}

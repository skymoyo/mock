package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

import java.lang.reflect.Type;

@Spi("java.lang.String")
public class ClassDeserializeByString extends AbstractClassDeserialize<String> {


    @Override
    public String deserializer(String jsonStr, Type type, String dataClass) {
        return jsonStr;
    }


}

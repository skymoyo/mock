package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

import java.lang.reflect.Type;
import java.math.BigDecimal;

@Spi("java.math.BigDecimal")
public class ClassDeserializeByBigDecimal extends AbstractClassDeserialize<BigDecimal> {

    @Override
    public BigDecimal deserializer(String jsonStr, Type type, String dataClass) {
        return new BigDecimal(jsonStr);
    }


}

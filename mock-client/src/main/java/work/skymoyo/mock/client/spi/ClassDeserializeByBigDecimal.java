package work.skymoyo.mock.client.spi;

import work.skymoyo.mock.common.spi.Spi;

import java.math.BigDecimal;

@Spi("java.math.BigDecimal")
public class ClassDeserializeByBigDecimal extends AbstractClassDeserialize<BigDecimal> {

    @Override
    public BigDecimal deserializer(String jsonStr) {
        return new BigDecimal(jsonStr);
    }


}

package work.skymoyo.mock.client.spi;

import org.springframework.stereotype.Service;
import work.skymoyo.mock.common.spi.Spi;

import java.util.Map;


@Spi("defCompile")
@Service("defCompile")
public class DefCompile implements MockCompile {

    @Override
    public Object encode(Object o) {
        return o;
    }

    @Override
    public Map<String, Object> decode(Map<String, Object> map) {
        return map;
    }


}

package work.skymoyo.test.controller;

import work.skymoyo.mock.client.spi.MockCompile;
import work.skymoyo.mock.common.spi.Spi;
import work.skymoyo.test.utils.AesUtil;

import java.util.Map;
import java.util.stream.Collectors;

@Spi("testSpi")
public class TestSpi implements MockCompile {


    public Object encode(Object o) {
        return AesUtil.encryptHex((String) o);
    }

    public Map<String, Object> decode(Map<String, Object> map) {
        return map.entrySet()
                .stream()
                .peek(e -> {
                    Object value = e.getValue();
                    e.setValue(AesUtil.decryptStr((String) value));
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
}

package work.skymoyo.test.controller;

import work.skymoyo.mock.client.spi.MockCompile;
import work.skymoyo.mock.common.spi.Spi;
import work.skymoyo.test.utils.AesUtil;

import java.util.Arrays;

@Spi("testSpi")
public class TestSpi implements MockCompile {


    public Object encode(Object o) {
        return AesUtil.encryptHex((String) o);
    }

    public Object decode(Object o) {
        return Arrays.stream(((Object[]) o)).map(s -> AesUtil.decryptStr((String) s)).toArray();
    }
}

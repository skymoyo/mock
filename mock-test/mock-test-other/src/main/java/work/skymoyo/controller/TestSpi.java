package work.skymoyo.controller;

import work.skymoyo.mock.client.spi.MockCompile;
import work.skymoyo.mock.common.spi.Spi;

@Spi("testSpi")
public class TestSpi implements MockCompile {
    @Override
    public Object encode(Object o) {
        return null;
    }

    @Override
    public Object decode(Object o) {
        return null;
    }
}

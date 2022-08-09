package work.skymoyo.test.controller;

import org.springframework.stereotype.Service;
import work.skymoyo.mock.client.spi.MockCompile;
import work.skymoyo.mock.common.spi.Spi;

@Spi("testSpi")
@Service
public class TestSpi implements MockCompile {
    public Object encode(Object o) {
        return null;
    }

    public Object decode(Object o) {
        return null;
    }
}

package work.skymoyo.test.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String helloSpi(String hello) {
        return "hello";
    }

}

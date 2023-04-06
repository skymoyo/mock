package work.skymoyo.test.service;

import org.springframework.stereotype.Service;
import work.skymoyo.test.model.Person;

import java.util.List;

@Service
public class TestService {

    public String helloSpi(String hello) {
        return "hello";
    }

    public List<Person> getPerson(String hello) {
        return null;
    }

}

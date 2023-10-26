package work.skymoyo.test.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.skymoyo.test.model.Person;
import work.skymoyo.test.utils.AesUtil;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private RemoteTestService remoteTestService;

    public String helloSpi(String hello) {
        return "hello";
    }

    public List<Person> getPerson(String hello) {
        return null;
    }

    public Person getPerson() {
        return null;
    }

    public List<Person> getPersonByAes(String hello) {
//        String person = remoteTestService.getPerson(AesUtil.encryptHex(hello));
//        String text = AesUtil.decryptStr(person);
        String person = remoteTestService.getPerson(hello);
        return JSON.parseArray(person, Person.class);
    }
}

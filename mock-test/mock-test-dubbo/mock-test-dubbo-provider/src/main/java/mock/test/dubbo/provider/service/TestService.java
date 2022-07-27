package mock.test.dubbo.provider.service;

import java.util.List;
import java.util.Map;

public interface TestService {


    void helloVoid();

    String helloString(String name);

    Integer helloInteger(Integer age);

    int helloInt(Integer age);

    List<Integer> helloList(String name, int size);

    Map<Object, Object> helloMap(int size, TestModel testModel);

    TestModel helloObject(Integer id, String name);

    TestModel helloObject(Integer id, String name, String desc);
}

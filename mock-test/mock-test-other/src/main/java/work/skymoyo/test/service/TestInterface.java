package work.skymoyo.test.service;

public interface TestInterface {

    String test();

    default String defaultTest(String a) {
        return a;
    }


}

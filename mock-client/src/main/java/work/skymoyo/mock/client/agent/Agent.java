package work.skymoyo.mock.client.agent;

import javassist.ClassPool;

public interface Agent {

    void proxy(ClassPool pool) throws Throwable;
}

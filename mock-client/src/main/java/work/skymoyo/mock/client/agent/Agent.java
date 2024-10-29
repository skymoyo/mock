package work.skymoyo.mock.client.agent;

import javassist.ClassPool;

import java.lang.instrument.Instrumentation;

public interface Agent {

    void proxy(String arg, Instrumentation instrumentation, ClassPool pool) throws Throwable;
}

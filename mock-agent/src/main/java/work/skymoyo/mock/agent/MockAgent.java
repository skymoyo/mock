package work.skymoyo.mock.agent;

import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

/**
 * mock 代理
 */
@Slf4j
public class MockAgent {

    public static void main(String[] args) throws Exception {
        MockAgent.premain(null, null);
    }

    public static void premain(String arg, Instrumentation instrumentation) throws Exception {
        try {
            log.debug("mock agent start");

            Class<?> agentManager = Class.forName("work.skymoyo.mock.client.agent.AgentManager");
            Method proxy = agentManager.getMethod("proxy", String.class, Instrumentation.class);
            proxy.invoke(agentManager.newInstance(), arg, instrumentation);

            log.debug("mock agent end\r\n");
        } catch (Throwable e) {
            //ignore
        }
    }

}

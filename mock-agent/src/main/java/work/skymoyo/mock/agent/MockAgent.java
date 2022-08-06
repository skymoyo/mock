package work.skymoyo.mock.agent;

import javassist.ClassPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

/**
 * mock 代理
 */
public class MockAgent {
    private static Logger logger = LoggerFactory.getLogger(MockAgent.class);


    public static void main(String[] args) throws Exception {
        MockAgent.premain(null, null);
    }

    public static void premain(String arg, Instrumentation instrumentation) throws Exception {
        try {
            logger.debug("mock agent start");
            ClassPool pool = new ClassPool(true);

            Class<?> agentManager = Class.forName("work.skymoyo.mock.client.agent.AgentManager");
            Method proxy = agentManager.getMethod("proxy", ClassPool.class);
            proxy.invoke(agentManager.newInstance(), pool);

            logger.debug("mock agent end");
        } catch (Throwable e) {
            logger.debug("mock agent error :{}", e.getMessage(), e);
        }
    }

}

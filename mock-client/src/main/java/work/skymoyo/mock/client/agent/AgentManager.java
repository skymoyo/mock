package work.skymoyo.mock.client.agent;

import javassist.ClassPool;
import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mock.common.spi.SpiManager;

import java.lang.instrument.Instrumentation;
import java.util.Map;

/**
 * {@link work.skymoyo.mock.agent.MockAgent#premain(java.lang.String, java.lang.instrument.Instrumentation)} 触发
 */
@Slf4j
public class AgentManager {

    public static void proxy(String arg, Instrumentation instrumentation) {
        Map<String, Agent> spiMap = SpiManager.getSpiMap(Agent.class);
        ClassPool pool = new ClassPool(true);

        spiMap.forEach((k, v) -> {
            try {
                log.debug("agent spi加载 :{}", k);
                v.proxy(arg, instrumentation, pool);
            } catch (Throwable e) {
                log.debug("agent spi加载:{}异常：{}", k, e.getMessage(), e);
            }
        });

    }
}

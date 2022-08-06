package work.skymoyo.mock.client.agent;

import javassist.ClassPool;
import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mock.common.spi.SpiManager;

import java.util.HashMap;

/**
 * work.skymoyo.mock.agent.MockAgent#premain(java.lang.String, java.lang.instrument.Instrumentation) 触发
 */
@Slf4j
public class AgentManager {

    public static void proxy(ClassPool pool) {
        HashMap<String, Agent> spiMap = SpiManager.getSpiMap(Agent.class);

        spiMap.forEach((k, v) -> {
            v.proxy(pool);
        });

    }
}

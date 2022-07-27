package work.skymoyo.mock.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * <groupId>com.alibaba</groupId>
 * <artifactId>dubbo</artifactId>
 * <version>2.6.0</version>
 * <p>
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

            //直接引用
            CtClass targetClass = pool.get("com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler");
            CtMethod method = targetClass.getDeclaredMethod("invoke");

            method.setBody("{"
                    + "return work.skymoyo.mock.client.utils.MockUtil.invoke(invoker,$$);"
                    + "}");

            // 编译，注意：如果类已经加载了，是不能重定义的，会报错 duplicate class definition....
            targetClass.toClass();


            //通过ReferenceConfig 获取引用
            CtClass referenceConfigCacheClass = pool.get("com.alibaba.dubbo.config.utils.ReferenceConfigCache");
            CtClass methodParams = pool.get("com.alibaba.dubbo.config.ReferenceConfig");
            CtClass[] patams = {methodParams};
            CtMethod referenceConfigCacheGetMethod = referenceConfigCacheClass.getDeclaredMethod("get", patams);
            CtClass etype = ClassPool.getDefault().get("java.lang.Throwable");

            referenceConfigCacheGetMethod.addCatch("{ " +
                    "return work.skymoyo.mock.client.utils.MockUtil.proxy($1.getInterfaceClass());" +
                    "}", etype);

            referenceConfigCacheClass.toClass();

            logger.debug("mock agent end");
        } catch (Throwable e) {
            logger.debug("mock agent error :{}", e.getMessage(), e);
        }
    }

}

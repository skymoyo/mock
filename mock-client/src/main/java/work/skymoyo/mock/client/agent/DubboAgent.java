package work.skymoyo.mock.client.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mock.common.spi.Spi;

/**
 * dubbo 2.6.0 代理
 */
@Spi
@Slf4j
public class DubboAgent implements Agent {

    @Override
    public void proxy(ClassPool pool) {
        try {
            //直接引用
            CtClass targetClass = pool.get("com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler");
            CtMethod method = targetClass.getDeclaredMethod("invoke");

            method.setBody("{"
                    + "return work.skymoyo.mock.client.utils.DubboMockUtil.invoke(invoker,$$);"
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
                    "return work.skymoyo.mock.client.utils.DubboMockUtil.proxy($1.getInterfaceClass());" +
                    "}", etype);

            referenceConfigCacheClass.toClass();
        } catch (Exception e) {
            log.warn("dubbo:2.6.0代理异常:{}", e.getMessage());
        }

    }
}

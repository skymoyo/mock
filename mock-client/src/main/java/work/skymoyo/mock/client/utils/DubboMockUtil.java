package work.skymoyo.mock.client.utils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * mock 工具类
 */
@Slf4j
public class DubboMockUtil {
    private static final ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
//    private static final Protocol refprotocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();


    private static final ConcurrentMap<Class, Object> CACHE = new ConcurrentHashMap<>();


    public static ConcurrentMap<Class, Object> getCACHE() {
        return CACHE;
    }

    //Object proxy, Method method, Object[] args
    public static Object invoke(Invoker<?> invoker, Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(invoker, args);
        }
        if ("toString".equals(methodName) && parameterTypes.length == 0) {
            return invoker.toString();
        }
        if ("hashCode".equals(methodName) && parameterTypes.length == 0) {
            return invoker.hashCode();
        }
        if ("equals".equals(methodName) && parameterTypes.length == 1) {
            return invoker.equals(args[0]);
        }

        try {
            if (MockContextUtil.isEnableMock()) {
                log.info("mock-agent 接口");
                String methodKey = DubboMockUtil.getMethodKey(method);

                return MethodMockUtil.proxyInvoker(methodKey, method.getReturnType(), args);
            }

        } catch (Throwable e) {
            log.warn("mock-agent 接口异常 执行原有逻辑{}", e.getMessage(), e);
        }

        return invoker.invoke(new RpcInvocation(method, args)).recreate();

    }


    public static String getMethodKey(Method sourceMethod) {
        return sourceMethod.getDeclaringClass().getName() + "#" + sourceMethod.getName();
    }

    /**
     * 无法通过 ReferenceConfig 获取引用 时
     * 生成一个 代理对象
     */
    public static <T> T proxy(Class<T> clazz) {

        return (T) CACHE.computeIfAbsent(clazz, t -> {
            try {

                //@see com.alibaba.dubbo.config.ReferenceConfig.createProxy

//                Invoker  invoker = refprotocol.refer(t,  new URL(Constants.CONSUMER,"127.0.0.1",9999));
                return (T) proxyFactory.getProxy(new Invoker<T>() {
                    @Override
                    public Class<T> getInterface() {
                        return clazz;
                    }

                    @Override
                    public Result invoke(Invocation invocation) throws RpcException {

                        return null;
                    }

                    @Override
                    public URL getUrl() {
//                        URL url = new URL(Constants.LOCAL_PROTOCOL, NetUtils.LOCALHOST, 0, interfaceClass.getName()).addParameters(map)
                        return new URL(Constants.CONSUMER, "127.0.0.1", 9999);
                    }

                    @Override
                    public boolean isAvailable() {
                        return true;
                    }

                    @Override
                    public void destroy() {

                    }
                });

                //todo 可以直接生成的接口直接调通mock ......

//                ClassPool pool = new ClassPool(true);
//                CtClass mockConfigCtClass = pool.makeClass("work.skymoyo.mock." + clazz.getSimpleName() + "impl");
//                CtClass referenceConfigCacheClass = pool.get(clazz.getName());
//
//                CtClass[] ctClasses = {referenceConfigCacheClass};
//                mockConfigCtClass.setInterfaces(ctClasses);
//
//                CtMethod[] declaredMethods = referenceConfigCacheClass.getDeclaredMethods();
//
//
//                for (int i = 0; i < declaredMethods.length; i++) {
//                    CtMethod declaredMethod = declaredMethods[i];
//
//                    CtMethod method = new CtMethod(declaredMethod.getReturnType(), declaredMethod.getName(), declaredMethod.getParameterTypes(), mockConfigCtClass);
//                    String source = "{"
//                            + "return null;"
//                            + "}";
//                    method.setBody(source);
//
//                    mockConfigCtClass.addMethod(method);
//                }
//
//                Class helloClass = mockConfigCtClass.toClass();
//                // 释放内存
//                mockConfigCtClass.detach();
//                return helloClass.newInstance();
            } catch (Exception e) {
                log.warn("[{}]创建代理接口异常[{}]", clazz, e.getMessage(), e);
            }
            return null;
        });


    }
}

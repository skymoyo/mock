package work.skymoyo.mock.client.agent;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.client.utils.MethodMockUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Aspect
@EnableAspectJAutoProxy
@Component
public class AspectAgentProxyExecutor {

    @Around("@annotation(work.skymoyo.mock.client.annotation.AopProxyAnnotation)")
    public Object aroundAgent(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        Class clazz = signature.getDeclaringType();
        String methodName = signature.getName();

        try {
            Object[] args = point.getArgs();
            Class<?>[] argsClass = Arrays.stream(args)
                    .map(Object::getClass)
                    .toArray(Class[]::new);

            Method method;
            if (clazz.isInterface()) {
                method = Arrays.stream(clazz.getMethods())
                        .filter(m -> Objects.equals(m.getName(), methodName))
                        .filter(m -> {
                            Class<?>[] parameterTypes = m.getParameterTypes();
                            if (parameterTypes.length != argsClass.length) {
                                return false;
                            }

                            for (int i = 0; i < parameterTypes.length; i++) {
                                if (parameterTypes[i] != argsClass[i]) {
                                    return false;
                                }
                            }
                            return true;
                        }).findFirst()
                        .get();
            } else {
                method = clazz.getDeclaredMethod(methodName, argsClass);
            }

            return MethodMockUtil.proxyInvoker(method, args);
        } catch (Exception e) {
            log.warn("aop mock 代理执行[{}#{}]异常:{}", clazz, methodName, e.getMessage(), e);
        }

        return point.proceed();
    }

}
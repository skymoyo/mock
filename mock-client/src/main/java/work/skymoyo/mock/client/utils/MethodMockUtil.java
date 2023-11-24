package work.skymoyo.mock.client.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.DefaultParameterNameDiscoverer;
import work.skymoyo.mock.client.client.MockClient;
import work.skymoyo.mock.client.client.MockClientManager;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MethodMockUtil {


    public static <T> T proxyInvoker(Method method, Object[] args) {

        try {
            MockClient first = MockClientManager.getOne();

            if (first == null) {
                throw new RuntimeException("mock client 未加载");
            }
            Type returnType = method.getGenericReturnType();
            String methodKey = BeanMockUtil.getMethodKey(method);
            Map<String, Object> reqArgs = MethodMockUtil.getReqArgs(method, args);
            return first.doMock(returnType, methodKey, reqArgs, true);
        } catch (Exception e) {
            log.warn("代理方法:{}  执行异常:{}", method, e.getMessage(), e);
            throw e;
        }
    }

    private final static DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    public static Map<String, Object> getReqArgs(Method method, Object[] args) {

        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        if (args.length > 0 && (parameterNames == null || parameterNames.length == 0)) {
            parameterNames = Arrays.stream(method.getParameters())
                    .map(Parameter::getName)
                    .toArray(String[]::new);
        }

        if (parameterNames == null || parameterNames.length == 0) {
            return new HashMap<>();
        }

        HashMap<String, Object> map = new HashMap<>(parameterNames.length);
        for (int i = 0; i < args.length; i++) {
            map.put(parameterNames[i], args[i]);
        }
        return map;
    }

}

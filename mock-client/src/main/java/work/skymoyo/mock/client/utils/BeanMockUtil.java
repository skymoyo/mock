package work.skymoyo.mock.client.utils;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BeanMockUtil {


    private static final ConcurrentHashMap<String, SoftReference<Method>> METHOD_MAP = new ConcurrentHashMap<>(16);


    public static Method getMethod(Class clazz, String methodName, Class[] params) {
        String key = clazz.getName() + "#" + methodName + "|" + Arrays.stream(params).map(Class::getName).collect(Collectors.joining("-"));

        return METHOD_MAP.computeIfAbsent(key, r -> {
            try {
                return new SoftReference<>(clazz.getMethod(methodName, params));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }).get();
    }


    private static final Map<Method, SoftReference<Type>> TYPE_MAP = new HashMap<>(16);

    public static Type getReturnType(Method method) {
        return TYPE_MAP.computeIfAbsent(method, r -> new SoftReference<>(method.getGenericReturnType())).get();
    }


    public static String getMethodKey(Method sourceMethod) {
        return sourceMethod.getDeclaringClass().getName() + "#" + sourceMethod.getName();
    }


}

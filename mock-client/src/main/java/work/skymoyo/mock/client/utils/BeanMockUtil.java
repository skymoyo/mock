package work.skymoyo.mock.client.utils;

import com.alibaba.fastjson.JSON;
import work.skymoyo.mock.client.spi.AbstractClassDeserialize;
import work.skymoyo.mock.client.spi.ClassDeserializeManager;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
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

    /**
     * 为了适配URL 将 类限定名 转成 URL
     *
     * @param packageName 限定名
     * @return
     */
    public static String getMockUrl(String packageName) {
        String methodKey = packageName.replaceAll("\\.", "/").replaceAll("#", "/");
        if (methodKey.startsWith("/")) {
            return methodKey;
        }
        return "/" + methodKey;
    }


    public static <T> T resolveRes(String res, Type type, String dataClass) {

        Class clazz;

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            clazz = (Class) parameterizedType.getRawType();
        } else {
            clazz = (Class) type;
        }

        AbstractClassDeserialize deserialize = ClassDeserializeManager.ofClass(clazz);
        if (deserialize != null) {
            return (T) deserialize.deserialize(res, type, dataClass);
        }

        return (T) JSON.parseObject(res, type);
    }


}

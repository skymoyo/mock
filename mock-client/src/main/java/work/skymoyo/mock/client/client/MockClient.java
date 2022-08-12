package work.skymoyo.mock.client.client;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public interface MockClient {

    /**
     * 为了适配URL 将 类限定名 转成 URL
     *
     * @param packageName 限定名
     * @return
     */
    default String getMockUrl(String packageName) {
        String methodKey = packageName.replaceAll("\\.", "/").replaceAll("#", "/");
        if (methodKey.startsWith("/")) {
            return methodKey;
        }
        return "/" + methodKey;
    }

    //todo 这里不太合适
    default <T> T resolveRes(String res, Class<T> clazz) {

        if (clazz.equals(void.class) || clazz.equals(Void.class)) {
            return (T) "";
        }
        if (!StringUtils.hasLength(res)) {
            return null;
        }

        if (clazz.equals(BigDecimal.class)) {
            return (T) new BigDecimal(res);
        }
        if (clazz.equals(Long.class) || clazz.equals(long.class)) {
            return (T) Long.valueOf(res);
        }
        if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            return (T) Integer.valueOf(res);
        }
        if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            return (T) Boolean.valueOf(res);
        }
        if (clazz.equals(String.class)) {
            return (T) res;
        }

        return JSON.parseObject(res, clazz);
    }


    /**
     * 执行mock
     *
     * @param returnClazz 返回类型
     * @param url         执行url 或者rpc 类限定名
     * @param isRpc       是否是rpc
     * @param paras       请求参数
     * @return
     */
    <R> R doMock(Class<R> returnClazz, String url, boolean isRpc, Object... paras);

}

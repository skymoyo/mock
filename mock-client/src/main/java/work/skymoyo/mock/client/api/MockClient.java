package work.skymoyo.mock.client.api;

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
        return packageName.replaceAll("\\.", "/").replaceAll("#", "/");
    }

    //todo 这里不太合适
    default <T> T resolveRes(String res, Class<T> clazz) {

        if (clazz.equals(void.class) || clazz.equals(Void.class)) {
            return (T) "";
        }
        if (StringUtils.isEmpty(res)) {
            return null;
        }
        if (clazz.equals(BigDecimal.class)) {
            return (T) new BigDecimal(res);
        } else if (clazz.equals(Long.class)) {
            return (T) Long.valueOf(res);
        } else if (clazz.equals(long.class)) {
            return (T) Long.valueOf(res);
        } else if (clazz.equals(Integer.class)) {
            return (T) Integer.valueOf(res);
        } else if (clazz.equals(int.class)) {
            return (T) Integer.valueOf(res);
        } else if (clazz.equals(String.class)) {
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

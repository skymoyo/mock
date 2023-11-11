package work.skymoyo.mock.client.client;

import java.lang.reflect.Type;
import java.util.Map;

public interface MockClient {


    /**
     * 执行mock
     *
     * @param type  返回类型
     * @param url   执行url 或者rpc 类限定名
     * @param paras 请求参数
     * @param isRpc 是否是rpc
     * @return
     */
    <R> R doMock(Type type, String url, Map<String, Object> paras, boolean isRpc);

}

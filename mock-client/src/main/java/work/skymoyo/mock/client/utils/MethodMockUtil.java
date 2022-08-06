package work.skymoyo.mock.client.utils;

import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mock.client.client.MockClient;
import work.skymoyo.mock.client.client.MockClientManager;

@Slf4j
public class MethodMockUtil {


    public static Object ProxyInvoker(String method, Class<?> returnType, Object[] args) {

        try {
            MockClient first = MockClientManager.getOne();
            return first.doMock(returnType, method, true, args);
        } catch (Exception e) {
            log.warn("代码方法异常{}", e.getMessage(), e);
        }
        return null;
    }


}

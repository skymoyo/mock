package work.skymoyo.mock.client.utils;

import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mock.client.client.MockClient;
import work.skymoyo.mock.client.client.MockClientManager;

@Slf4j
public class MethodMockUtil {


    public static <T> T proxyInvoker(String methodName, Class<T> returnType, Object[] args) {

        try {
            MockClient first = MockClientManager.getOne();

            if (first == null) {
                throw new RuntimeException("mock client 未不加载");
            }

            return first.doMock(returnType, methodName, true, args);
        } catch (Exception e) {
            log.warn("代码方法异常{}", e.getMessage(), e);
            throw e;
        }

    }


}

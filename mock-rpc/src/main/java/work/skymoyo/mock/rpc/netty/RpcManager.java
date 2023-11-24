package work.skymoyo.mock.rpc.netty;

import org.springframework.stereotype.Component;
import work.skymoyo.mock.common.model.MockResp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * netty异步转同步 管理者
 */
@Component
public class RpcManager {

    private static final ConcurrentMap<String, RpcFuture<Object>> HOLDER = new ConcurrentHashMap<>();


    public void add(String uuid, RpcFuture<Object> rpcFuture) {
        HOLDER.put(uuid, rpcFuture);
    }


    public void setResp(String uuid, MockResp<Object> resp) {
        RpcFuture<Object> rpcFuture = HOLDER.get(uuid);
        if (rpcFuture != null) {
            rpcFuture.setResponse(resp);
            HOLDER.remove(uuid);
        }
    }

}

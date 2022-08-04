package work.skymoyo.mock.rpc.netty;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * netty异步转同步 管理者
 */
@Component
public class RpcManager {

    private static final ConcurrentMap<String, RpcFuture> holder = new ConcurrentHashMap<>();


    public void add(String uuid, RpcFuture rpcFuture) {
        holder.put(uuid, rpcFuture);
    }


    public void setResp(String uuid, Object resp) {
        holder.get(uuid).setResponse(resp);
    }

}

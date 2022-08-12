package work.skymoyo.mock.rpc.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mock.common.model.MockReq;

import java.util.concurrent.*;

/**
 * netty异步转同步
 *
 * @param <T>
 */
@Slf4j
public class RpcFuture<T> implements Future<T> {

    private CountDownLatch latch = new CountDownLatch(1);

    private T response;

    private Channel channel;

    public RpcFuture(Channel channel) {
        this.channel = channel;
    }

    public void sendMsg(MockReq<T> req) {
        log.info("mock客户端发送消息：{}", JSON.toJSONString(req, SerializerFeature.PrettyFormat));
        channel.writeAndFlush(req);
    }

    // 用于设置响应结果，并且做countDown操作，通知请求线程
    public void setResponse(T response) {
        this.response = response;
        latch.countDown();
    }


    @Override
    public boolean isDone() {
        return response != null;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        latch.await();
        return this.response;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (latch.await(timeout, unit)) {
            return this.response;
        }
        return null;
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

}

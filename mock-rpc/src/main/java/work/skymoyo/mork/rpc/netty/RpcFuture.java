package work.skymoyo.mork.rpc.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mock.common.model.MockReq;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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


    @Override
    public boolean isDone() {
        if (response != null) {
            return true;
        }
        return false;
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

    // 用于设置响应结果，并且做countDown操作，通知请求线程
    public void setResponse(T response) {
        this.response = response;
        latch.countDown();
    }


    //-------------------分割线---------------------------


    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isCancellable() {
        return false;
    }

    @Override
    public Throwable cause() {
        return null;
    }

    @Override
    public Future<T> addListener(GenericFutureListener<? extends Future<? super T>> listener) {
        return null;
    }

    @Override
    public Future<T> addListeners(GenericFutureListener<? extends Future<? super T>>... listeners) {
        return null;
    }

    @Override
    public Future<T> removeListener(GenericFutureListener<? extends Future<? super T>> listener) {
        return null;
    }

    @Override
    public Future<T> removeListeners(GenericFutureListener<? extends Future<? super T>>... listeners) {
        return null;
    }

    @Override
    public Future<T> sync() throws InterruptedException {
        return null;
    }

    @Override
    public Future<T> syncUninterruptibly() {
        return null;
    }

    @Override
    public Future<T> await() throws InterruptedException {
        return null;
    }

    @Override
    public Future<T> awaitUninterruptibly() {
        return null;
    }

    @Override
    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public boolean await(long timeoutMillis) throws InterruptedException {
        return false;
    }

    @Override
    public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
        return false;
    }

    @Override
    public boolean awaitUninterruptibly(long timeoutMillis) {
        return false;
    }

    @Override
    public T getNow() {
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

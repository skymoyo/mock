package work.skymoyo.mock.client.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.client.spi.CompileManager;
import work.skymoyo.mock.client.spi.MockCompile;
import work.skymoyo.mock.common.enums.OptType;
import work.skymoyo.mock.common.exception.MockException;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.rpc.config.MockConf;
import work.skymoyo.mock.rpc.netty.ChannelManager;
import work.skymoyo.mock.rpc.netty.ClientInitializer;
import work.skymoyo.mock.rpc.netty.RpcFuture;
import work.skymoyo.mock.rpc.netty.RpcManager;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ConditionalOnBean(ClientInitializer.class)
public final class MockNettyClient implements MockClient, ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private RpcManager rpcManager;
    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private CompileManager compileManager;
    @Autowired
    private MockConf mockConf;

    private MockCompile mockCompile;

    @Override
    public void onApplicationEvent(@Nullable ApplicationReadyEvent event) {
        mockCompile = compileManager.getSpiMap(mockConf.getCompile(), event);
    }


    @Override
    public <R> R doMock(Class<R> returnClazz, String url, boolean isRpc, Object... paras) {

        String uuid = UUID.randomUUID().toString();

        RpcFuture<Object> future = new RpcFuture<>(channelManager.getChannel());
        rpcManager.add(uuid, future);

        MockReq<Object> req = new MockReq<>();
        req.setUuid(uuid);
        req.setOpt(OptType.MOCK);
        req.setRoute(this.getMockUrl(url));
        req.setData(mockCompile.decode(paras));
        future.sendMsg(req);

        try {
            Object resp = future.get(10, TimeUnit.SECONDS);
            if (resp == null) {
                throw new MockException("mockNettyClient timeout");
            }

            return this.resolveRes((String) mockCompile.encode(resp), returnClazz);
        } catch (MockException e) {
            throw e;
        } catch (Exception e) {
            throw new MockException(e.getMessage());
        }
    }


}

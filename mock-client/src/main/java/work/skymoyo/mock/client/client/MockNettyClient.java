package work.skymoyo.mock.client.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.client.spi.CompileManager;
import work.skymoyo.mock.client.spi.MockCompile;
import work.skymoyo.mock.common.enums.OptType;
import work.skymoyo.mock.common.exception.MockException;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mork.rpc.config.MockConf;
import work.skymoyo.mork.rpc.netty.ChannelManager;
import work.skymoyo.mork.rpc.netty.ClientInitializer;
import work.skymoyo.mork.rpc.netty.RpcFuture;
import work.skymoyo.mork.rpc.netty.RpcManager;

import java.util.UUID;

@Slf4j
@Component
@ConditionalOnBean(ClientInitializer.class)
public final class MockNettyClient implements MockClient {

    @Autowired
    private RpcManager rpcManager;
    @Autowired
    private ChannelManager channelManager;

    @Autowired
    private CompileManager compileManager;
    @Autowired
    private MockConf mockConf;

    private MockCompile mockCompile;


    /**
     * 从这里直接获取，否者有NPE风险
     *
     * @return
     */
    private MockCompile getMockCompile() {
        return mockCompile == null ? this.mockCompile = compileManager.getSpiMap(mockConf.getCompile()) : mockCompile;
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
        req.setData(this.getMockCompile().encode(paras));
        future.sendMsg(req);

        try {
            return this.resolveRes((String) this.getMockCompile().decode(future.get()), returnClazz);
        } catch (Exception e) {
            throw new MockException(e.getMessage());
        }
    }


}

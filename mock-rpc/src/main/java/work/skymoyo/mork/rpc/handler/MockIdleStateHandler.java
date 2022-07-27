package work.skymoyo.mork.rpc.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mork.rpc.netty.ClientInitializer;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MockIdleStateHandler extends IdleStateHandler {

    private static final int IDLE_TIME = 90;

    private ClientInitializer initializer;

    public MockIdleStateHandler(ClientInitializer initializer) {
        super(IDLE_TIME, 0, 0, TimeUnit.SECONDS);
        this.initializer = initializer;
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt)  {
        log.info("connection [{}] close for no heartbeat.",ctx.channel().toString());
        ctx.channel().close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws InterruptedException {
       initializer.reconnect();
    }
}

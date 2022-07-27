package work.skymoyo.mock.core.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MockIdleStateHandler extends IdleStateHandler {

    private static final int IDLE_TIME = 90;

    public MockIdleStateHandler() {
        super(IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt)  {
        log.info("connection [{}] close for no heartbeat.",ctx.channel().toString());
        ctx.channel().close();
    }

}

package work.skymoyo.mock.rpc.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mock.common.model.Heartbeat;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HeartBeatHandler extends SimpleChannelInboundHandler<Heartbeat> {

    private static final int HEARTBEAT_INTERVAL = 30;

    private static final Heartbeat HEART_BEAT = new Heartbeat("ping");


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Heartbeat msg) throws Exception {
        log.debug("mock客户端接收到 {}", msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.heartbeat(ctx);
        super.channelActive(ctx);
    }

    private void heartbeat(ChannelHandlerContext ctx) {

        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.channel().writeAndFlush(HEART_BEAT);
                this.heartbeat(ctx);
                log.debug("mock客户端发送 {}", HEART_BEAT);
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);

    }
}

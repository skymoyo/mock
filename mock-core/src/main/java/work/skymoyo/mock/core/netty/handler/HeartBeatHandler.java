package work.skymoyo.mock.core.netty.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import work.skymoyo.mock.common.model.Heartbeat;

@Slf4j
public class HeartBeatHandler extends SimpleChannelInboundHandler<Heartbeat> {


    private static final Heartbeat HEART_BEAT = new Heartbeat("pong");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Heartbeat heartbeat) throws Exception {
        log.debug("mock服务端接收到 {}", heartbeat);
        ctx.writeAndFlush(HEART_BEAT);
    }
}

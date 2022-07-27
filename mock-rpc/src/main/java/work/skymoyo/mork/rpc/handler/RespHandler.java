package work.skymoyo.mork.rpc.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mork.rpc.netty.RpcManager;

@Slf4j
@Component
@ChannelHandler.Sharable
public class RespHandler extends SimpleChannelInboundHandler<MockResp> {

    @Autowired
    private RpcManager rpcManager;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MockResp resp) {
        log.info("mock客户端收到消息: [{}]", JSON.toJSONString(resp, SerializerFeature.PrettyFormat));
        rpcManager.setResp(resp.getUuid(), resp.getData());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        log.error("mock客户端异常.", cause);

    }
}

package work.skymoyo.mock.core.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.common.model.MockResp;
import work.skymoyo.mock.core.service.MockContext;
import work.skymoyo.mock.core.service.MockService;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@ChannelHandler.Sharable
public class MockReqHandler extends SimpleChannelInboundHandler<MockReq> {

    @Autowired
    private MockService mockService;

    @Autowired
    private MockContext mockContext;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MockReq req) {
        log.info("mock服务端接收到消息{}", JSON.toJSONString(req, SerializerFeature.PrettyFormat));

        mockContext.setLocalMockReq(req);

        MockResp<Object> resp = new MockResp<>();
        resp.setSuccess(true);
        resp.setUuid(req.getUuid());
        MockDataBo dataBo = mockService.mock(req);
        resp.setDataClass(dataBo.getDataClass());
        resp.setData(dataBo.getData());

        ctx.channel().writeAndFlush(resp);

        log.info("[{}]mock服务端返回消息{}", req.getUuid(), JSON.toJSONString(resp, SerializerFeature.PrettyFormat));
        mockContext.remove();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("mock handler catch the operation exception.", cause);
        MockReq req = mockContext.getLocalMockReq();
        mockContext.remove();

        //这里有问题，断开链接，也会进入这里
        if (cause instanceof IOException && Objects.equals(cause.getMessage(), "远程主机强迫关闭了一个现有的连接。")) {
            return;
        }

        MockResp resp = new MockResp<>();
        resp.setSuccess(false);
        resp.setUuid(req.getUuid());
        resp.setMsg(cause.getMessage());

        ctx.channel().writeAndFlush(resp);
        log.info("[{}]异常返回:{}", req.getUuid(), JSON.toJSONString(resp, SerializerFeature.PrettyFormat));
    }

}

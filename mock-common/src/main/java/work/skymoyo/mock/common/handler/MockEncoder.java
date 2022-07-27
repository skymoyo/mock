package work.skymoyo.mock.common.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import work.skymoyo.mock.common.MockObjectManager;
import work.skymoyo.mock.common.model.BaseObject;
import work.skymoyo.mock.common.model.Constant;

import java.nio.charset.StandardCharsets;

/**
 * v.1.0.0
 * | magic-number (4bytes) | ver (1byte) | object (1byte) | content-length (4bytes) | ... content |
 */
public class MockEncoder extends MessageToByteEncoder<BaseObject> {

    private MockObjectManager mockObjectManager;


    public MockEncoder(MockObjectManager mockObjectManager) {
        this.mockObjectManager = mockObjectManager;
    }

    private MockEncoder() {
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, BaseObject baseObject, ByteBuf out) throws Exception {

        out.writeInt(Constant.MAGIC_NUM);

        out.writeByte(Constant.VER);

        out.writeByte(baseObject.getObject());

        byte[] data = JSON.toJSONString(baseObject).getBytes(StandardCharsets.UTF_8);

        out.writeInt(data.length);

        out.writeBytes(data);

    }
}

package work.skymoyo.mock.common.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import work.skymoyo.mock.common.MockObjectManager;
import work.skymoyo.mock.common.exception.MockException;
import work.skymoyo.mock.common.model.BaseObject;
import work.skymoyo.mock.common.model.Constant;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * v.1.0.0
 * | magic-number (4bytes) | ver (1byte) | object (1byte) | content-length (4bytes) | ... content |
 */

public class MockDecoder extends MessageToMessageDecoder<ByteBuf> {

    private MockObjectManager mockObjectManager;


    public MockDecoder(MockObjectManager mockObjectManager) {
        this.mockObjectManager = mockObjectManager;
    }

    private MockDecoder() {
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {

        final int magic = byteBuf.readInt();
        if (!Objects.equals(magic, Constant.MAGIC_NUM)) {
            throw new MockException("数据不合法");
        }

        byte version = byteBuf.readByte();
        byte b = byteBuf.readByte();

        Class<? extends BaseObject> clazz = mockObjectManager.getObject(b);

        final int length = byteBuf.readInt();
        final byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        BaseObject baseObject = JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), clazz);
        out.add(baseObject);

    }


}

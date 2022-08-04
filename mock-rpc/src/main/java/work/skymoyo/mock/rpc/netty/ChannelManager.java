package work.skymoyo.mock.rpc.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ChannelManager {

    private Channel channel;


    public Channel getChannel() {
        return channel;
    }

    public void refreshChannel(Channel channel) {
        this.channel = channel;
        this.auth(channel);
    }


    public void auth(Channel channel) {

        ChannelFuture f = channel.writeAndFlush("");

        f.addListener(rs -> {
            if (rs.isSuccess()) {
                log.info("authorization process done.");
            }
        });

    }

    /**
     * 客户端关闭
     */
    public void close() {
        //关闭客户端套接字
        if (channel != null) {
            channel.close();
            log.info("channel close done.");
        }
    }

}

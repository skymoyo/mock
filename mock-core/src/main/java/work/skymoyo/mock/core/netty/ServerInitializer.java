package work.skymoyo.mock.core.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.common.MockObjectManager;
import work.skymoyo.mock.common.handler.MockDecoder;
import work.skymoyo.mock.common.handler.MockEncoder;
import work.skymoyo.mock.core.config.MockConf;
import work.skymoyo.mock.core.netty.handler.HeartBeatHandler;
import work.skymoyo.mock.core.netty.handler.MockIdleStateHandler;
import work.skymoyo.mock.core.netty.handler.MockReqHandler;

import javax.annotation.PostConstruct;


@Slf4j
@Component
public class ServerInitializer {

    @Autowired
    private MockConf mockConf;

    @Autowired
    private MockReqHandler mockReqHandler;


    @Autowired
    private MockObjectManager mockObjectManager;


    @PostConstruct
    private void init() {
        final NioEventLoopGroup boss = new NioEventLoopGroup();
        final NioEventLoopGroup work = new NioEventLoopGroup(mockConf.getWorkThreads());

        final ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new MockDecoder(mockObjectManager))
                                .addLast(mockReqHandler)
                                .addLast(new MockEncoder(mockObjectManager))
                                .addLast(new MockIdleStateHandler())
                                .addLast(new HeartBeatHandler())
                        ;
                    }
                });

        bootstrap.bind(mockConf.getPort())
                .addListener(f -> {
                    if (f.isSuccess()) {
                        log.info("mock core has started on port {} .", mockConf.getPort());
                    }
                });
    }
}
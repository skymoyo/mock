package work.skymoyo.mork.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import work.skymoyo.mock.common.MockObjectManager;
import work.skymoyo.mock.common.handler.MockDecoder;
import work.skymoyo.mock.common.handler.MockEncoder;
import work.skymoyo.mork.rpc.config.MockConf;
import work.skymoyo.mork.rpc.handler.HeartBeatHandler;
import work.skymoyo.mork.rpc.handler.MockIdleStateHandler;
import work.skymoyo.mork.rpc.handler.RespHandler;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ConditionalOnProperty(name = "mock.config.rpc", havingValue = "mockNettyClient", matchIfMissing = true)
public class ClientInitializer implements ApplicationContextAware, ApplicationListener<ContextClosedEvent> {

    @Autowired
    private MockConf mockConf;
    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private RespHandler respHandler;
    @Autowired
    private MockObjectManager mockObjectManager;

    private Bootstrap bootstrap;

    private ApplicationContext ctx;


    @PostConstruct
    private void init() throws InterruptedException {

        final NioEventLoopGroup loopGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new MockDecoder(mockObjectManager))
                                .addLast(respHandler)
                                .addLast(new MockEncoder(mockObjectManager))
                                .addLast(new MockIdleStateHandler(ctx.getBean(ClientInitializer.class)))
                                .addLast(new HeartBeatHandler())
                        ;
                    }
                });

        this.connectWithRetry(mockConf.getRetry());
    }


    /**
     * @Description: 指数退避方式重连机制
     * @Param:
     * @return:
     * @Author: fyang
     * @Date: 2021/1/5
     */
    private void connectWithRetry(int retry) {
        bootstrap.connect(mockConf.getHost(), mockConf.getPort())
                .addListener(f -> {
                    if (f.isSuccess()) {
                        log.info("connection succeeded.");
                        Channel channel = ((ChannelFuture) f).channel();

                        channelManager.refreshChannel(channel);

                    } else if (retry == 0) {
                        connectWithRetry(mockConf.getRetry());
                    } else {
                        // current round.
                        int currentRound = (mockConf.getRetry() - retry) + 1;
                        // current delay.
                        int delay = 1 << currentRound;

                        log.info("retry to get connection, round: [{}].", currentRound);
                        bootstrap.config()
                                .group()
                                .schedule(() -> connectWithRetry(retry - 1), delay, TimeUnit.SECONDS);
                    }
                });

    }

    public void reconnect() {
        this.connectWithRetry(mockConf.getRetry());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        channelManager.close();
    }
}

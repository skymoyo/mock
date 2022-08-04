package work.skymoyo.mock.rpc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Data
@Configuration
@ConfigurationProperties(prefix = "mock.config")
public class MockConf {

    private String host;

    private Integer port;

    private String prefix;

    private String ak;

    private String sk;

    private Integer retry;

    private String compile;

    private String rpc;

    public Integer getRetry() {
        return Optional.ofNullable(retry).orElse(5);
    }

    public String getCompile() {
        return Optional.ofNullable(compile).orElse("defCompile");
    }

    public String getRpc() {
        return Optional.ofNullable(rpc).orElse("mockNettyClient");
    }

}

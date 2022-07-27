package work.skymoyo.mock.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Data
@Configuration
@ConfigurationProperties(prefix = "mock.config")
public class MockConf {


    private Integer port;

    private Integer workThreads;


    public Integer getPort() {
        return Optional.ofNullable(port).orElse(18081);
    }

    public Integer getWorkThreads() {
        return Optional.ofNullable(workThreads).orElse(16);
    }
}

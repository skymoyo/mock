package work.skymoyo.mock.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "mock.config.enable", havingValue = "true")
@ComponentScan(basePackages = {"work.skymoyo"})
public class AutoConfig {


}

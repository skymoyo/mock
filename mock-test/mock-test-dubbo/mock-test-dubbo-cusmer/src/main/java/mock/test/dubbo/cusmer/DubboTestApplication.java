package mock.test.dubbo.cusmer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.ArrayList;

@ImportResource({"classpath:provider.xml"})
@SpringBootApplication(scanBasePackages = "mock.test")
public class DubboTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboTestApplication.class, args);
    }

}

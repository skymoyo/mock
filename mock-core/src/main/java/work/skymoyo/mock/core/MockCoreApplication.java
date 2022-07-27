package work.skymoyo.mock.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "work.skymoyo.mock.core.resource.dao")
@SpringBootApplication(scanBasePackages = "work.skymoyo")
public class MockCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockCoreApplication.class, args);
    }

}

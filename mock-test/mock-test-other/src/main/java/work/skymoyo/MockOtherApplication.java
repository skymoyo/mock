package work.skymoyo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "work.skymoyo.test")
public class MockOtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockOtherApplication.class, args);
    }

}

package work.skymoyo.mock.rpc;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class MorkRpcApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MorkRpcApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}

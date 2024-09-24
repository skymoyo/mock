package work.skymoyo.mock.core.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private LogInterceptor logInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor) // 设置拦截器实现
                .addPathPatterns("/admin/**")     // 设置要拦截的路径
                .excludePathPatterns("/admin/login", "/admin/captchaImage", "/admin/mock/error")    // 设置要忽略拦截的路径
        ;
    }

}

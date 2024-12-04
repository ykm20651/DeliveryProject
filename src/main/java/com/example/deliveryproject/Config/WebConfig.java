package com.example.deliveryproject.Config;

import com.example.deliveryproject.Interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 인증이 필요한 URL에 대해 인터셉터를 적용
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**") // 필요한 경로에 적용
                .excludePathPatterns("/api/users/signup", "/api/users/login"); // 예외 처리
    }
}

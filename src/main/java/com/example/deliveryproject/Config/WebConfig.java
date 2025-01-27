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
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/home/users/**") // `/home/users/...` 경로에 인터셉터 적용
                .excludePathPatterns(
                        "/home/users/signup", // 회원가입 예외
                        "/home/users/login",  // 로그인 예외
                        "/home/users/logout"  // 로그아웃 예외 (필요하면 추가)
                );
    }
}


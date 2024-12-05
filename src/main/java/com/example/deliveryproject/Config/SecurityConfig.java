package com.example.deliveryproject.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (개발 단계에서만)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login.html", "/signup.html", "/home/users/signup", "/home/users/login").permitAll() // 인증 없이 접근 가능
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )
                .formLogin(login -> login
                        .loginPage("/login.html") // 로그인 페이지 경로
                        .loginProcessingUrl("/home/users/login") // 로그인 처리 URL
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 이동할 경로
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html")
                        .permitAll()
                );

        return http.build();
    }
}

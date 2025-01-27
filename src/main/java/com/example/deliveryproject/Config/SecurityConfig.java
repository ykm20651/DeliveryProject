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
                // CSRF 비활성화 (실제 배포 환경에서는 활성화 권장)
                .csrf(csrf -> csrf.disable())

                // 요청 URL에 따른 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login.html",         // 로그인 페이지
                                "/signup.html",        // 회원가입 페이지
                                "/home/users/signup",  // 회원가입 API
                                "/home/users/login"    // 로그인 API
                        ).permitAll()               // 인증 없이 접근 가능
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )

                // 폼 로그인 설정
                .formLogin(login -> login
                        .loginPage("/login.html")       // 로그인 페이지 경로
                        .loginProcessingUrl("/home/users/login") // 로그인 처리 URL
                        .defaultSuccessUrl("/", true)   // 로그인 성공 시 이동 경로
                        .failureUrl("/login.html?error=true") // 로그인 실패 시 이동 경로
                        .permitAll()
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/home/users/logout") // 로그아웃 API 경로
                        .logoutSuccessUrl("/login.html") // 로그아웃 성공 시 이동 경로
                        .invalidateHttpSession(true)    // 세션 무효화
                        .deleteCookies("JSESSIONID")    // 쿠키 삭제
                        .permitAll()
                );

        return http.build();
    }
}

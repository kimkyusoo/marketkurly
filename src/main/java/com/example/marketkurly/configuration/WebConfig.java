package com.example.marketkurly.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 스프링이 기동 될 때 먼저 읽는 설정 파일
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override // addCorsMappings 메소드 오버라이드
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT","DELETE","HEAD","OPTIONS")
                .exposedHeaders("*")
                .allowedHeaders("*")
                .allowCredentials(true)

                .maxAge(3600);
        // 경로 패턴 설정, 리소스 공유 허용 할 Origin(출처), 허용할 메소드, 프리플라이트 리퀘스트 캐싱 시간(초)설정
    }
}

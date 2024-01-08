//WebConfig
package com.bondsbackend.ms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешаем CORS запросы для всех URL
                .allowedMethods("*"); // Разрешаем все методы (GET, POST, PUT, DELETE, etc.)
    }
}

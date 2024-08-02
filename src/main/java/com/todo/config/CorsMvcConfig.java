package com.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
* @packageName    : com.todo.config
* @fileName        : CorsMvcConfig.java
* @author        : leejongseop
* @description            :
* ===========================================================
           AUTHOR             NOTE
* -----------------------------------------------------------
        leejongseop       최초 생성
*/
@Configuration
public class CorsMvcConfig implements WebMvcConfigurer  {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Access", "Content-Type")
                .exposedHeaders("Access")
                .allowCredentials(true);
    }
}

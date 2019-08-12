package com.danny.javablogaggregatorspringboot.config;

import com.danny.javablogaggregatorspringboot.service.ConfigurationInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor

@Configuration
public class WebXmlSpringBoot implements WebMvcConfigurer {

    private ConfigurationInterceptor configurationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(configurationInterceptor);
    }

}

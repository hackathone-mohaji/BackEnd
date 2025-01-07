package com.mohaji.hackathon.common.config;


import com.mohaji.hackathon.domain.Image.config.ImageConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CustomWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/gen/**")
                .addResourceLocations("file:///" + ImageConfig.getImageDirectory() + "/");
    }

}

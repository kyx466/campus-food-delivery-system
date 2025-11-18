package com.ch.schoolwaimai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("=== 静态资源映射配置 ===");
        System.out.println("上传目录配置: " + uploadDir);

        // 方案1：使用类路径映射（推荐）
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/uploads/");

        System.out.println("映射配置: /food/images/** -> classpath:/static/uploads/");
    }
}
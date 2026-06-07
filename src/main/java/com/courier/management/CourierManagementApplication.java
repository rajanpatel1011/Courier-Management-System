package com.courier.management;

import com.courier.management.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class CourierManagementApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CourierManagementApplication.class);
        app.setDefaultProperties(Map.of("spring.profiles.active", "dev"));
        app.run(args);
    }
}
package com.courier.management;

import com.courier.management.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class CourierManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourierManagementApplication.class, args);
    }
}
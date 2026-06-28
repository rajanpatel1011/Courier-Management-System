package com.courier.management.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name = "Courier Management System";
    private String version = "1.0.0";
    private String publicBaseUrl = "http://localhost:8080";
    private Qr qr = new Qr();

    @Getter
    @Setter
    public static class Qr {
        private int refreshIntervalSeconds = 15;
    }
}

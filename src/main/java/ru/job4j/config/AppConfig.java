package ru.job4j.config;

import org.springframework.beans.factory.annotation.Value;

public class AppConfig {

    @Value("${telegram.bot.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.url}")
    private String appUrl;

    @Value("${app.timeout}")
    private int appTimeout;

    public void printConfig() {
        System.out.println(appName);
        System.out.println(appVersion);
        System.out.println(appUrl);
        System.out.println(appTimeout);
    }
}

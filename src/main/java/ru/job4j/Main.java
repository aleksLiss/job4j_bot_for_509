package ru.job4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.job4j.controller.TelegramBotService;
import ru.job4j.model.Content;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner initTelegramApi(ApplicationContext context) {
        return args -> {
            var bot = context.getBean(TelegramBotService.class);
            bot.receive(new Content());
        };
    }
}
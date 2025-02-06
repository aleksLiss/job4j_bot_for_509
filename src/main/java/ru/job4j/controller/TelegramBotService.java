package ru.job4j.controller;

import org.springframework.stereotype.Service;
import ru.job4j.handler.BotCommandHandler;
import ru.job4j.model.Content;

@Service
public class TelegramBotService {

    private final BotCommandHandler botCommandHandler;

    public TelegramBotService(BotCommandHandler botCommandHandler) {
        this.botCommandHandler = botCommandHandler;
    }

    public void receive(Content content) {
        botCommandHandler.receive(content);
    }
}

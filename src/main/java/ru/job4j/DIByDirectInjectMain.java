package ru.job4j;

import ru.job4j.controller.TelegramBotService;
import ru.job4j.handler.BotCommandHandler;
import ru.job4j.model.Content;

public class DIByDirectInjectMain {
    public static void main(String[] args) {
        var handler = new BotCommandHandler();
        var tg = new TelegramBotService(handler);
        tg.receive(new Content());
    }
}

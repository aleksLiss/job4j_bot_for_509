package ru.job4j.handler;

import org.springframework.stereotype.Service;
import ru.job4j.model.Content;

@Service
public class BotCommandHandler {

    void receive(Content content) {
        System.out.println(content);
    }
}

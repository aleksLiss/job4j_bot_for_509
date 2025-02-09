package ru.job4j.bussines;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Runner {

    void run(Update update);
}

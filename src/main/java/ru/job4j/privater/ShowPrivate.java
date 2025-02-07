package ru.job4j.privater;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ShowPrivate {

    String getUserFirstName(Update update);

    String getUserLastName(Update update);

    Long getUserId(Update update);
}

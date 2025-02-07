package ru.job4j.privater;

import org.telegram.telegrambots.meta.api.objects.Update;

public class UserShowPrivate implements ShowPrivate {
    @Override
    public String getUserFirstName(Update update) {
        return update.getMessage().getFrom().getFirstName();
    }

    @Override
    public String getUserLastName(Update update) {
        return update.getMessage().getFrom().getLastName();
    }

    @Override
    public Long getUserId(Update update) {
        return update.getMessage().getFrom().getId();
    }
}

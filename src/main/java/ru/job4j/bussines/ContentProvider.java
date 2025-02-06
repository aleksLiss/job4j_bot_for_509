package ru.job4j.bussines;

import ru.job4j.model.Content;

public interface ContentProvider {

    Content sendMsg(Long chatId);
}

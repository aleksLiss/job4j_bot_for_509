package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Content;
import ru.job4j.bussines.ContentProvider;

import java.util.List;
import java.util.Random;

@Service
public class RecommendationEngine {
    private final List<ContentProvider> contents;
    private static final Random RND = new Random(System.currentTimeMillis());

    public RecommendationEngine(List<ContentProvider> contents) {
        this.contents = contents;
    }

    public Content recommendFor(Long chatId) {
        var index = RND.nextInt(0, contents.size());
        return contents.get(index).sendMsg(chatId);
    }
}

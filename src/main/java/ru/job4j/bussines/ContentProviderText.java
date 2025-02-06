package ru.job4j.bussines;

import org.springframework.stereotype.Component;
import ru.job4j.model.Content;

@Component
public class ContentProviderText implements ContentProvider {
    @Override
    public Content sendMsg(Long chatId) {
        Content content = new Content(chatId);
        content.setText("Сам ты пидор.");
        return content;
    }
}

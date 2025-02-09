package ru.job4j.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.job4j.bussines.Runner;
import ru.job4j.privater.ShowPrivate;
import ru.job4j.privater.UserShowPrivate;
import ru.job4j.repository.UserRepository;

@Service
public class TgRemoteService extends TelegramLongPollingBot implements Runner {

    private final String botName;
    private final String botToken;
    private final UserRepository userRepository;
    private final ShowPrivate showPrivate;

    public TgRemoteService(@Value("${telegram.bot.name}") String botName,
                           @Value("${telegram.bot.token}") String botToken,
                           UserRepository userRepository,
                           ShowPrivate showPrivate) {
        this.botName = botName;
        this.botToken = botToken;
        this.userRepository = userRepository;
        this.showPrivate = new UserShowPrivate();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            run(update);
        }
    }

    @Override
    public void run(Update update) {
        String command = update.getMessage().getText();
        switch (command) {
            case "/add":
                help(update);
//                add();
                return;
            case "/delete":
                help(update);
//                delete();
                return;
            case "/start":
                help(update);
//                start();
                return;
            case "/help":
                help(update);
                return;
            default:
                help(update);
                return;
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    private void sendMsg(SendMessage message) {
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void add(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
//            update.getMessage().getFrom();
//            User user = new User();
        message.setChatId(chatId);
        message.setText("Вы написали: " + messageText + "\n" + showPrivate.getAllInfo(update) + update.hasChannelPost());
        sendMsg(message);
    }

    private void delete(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
//            update.getMessage().getFrom();
//            User user = new User();
        message.setChatId(chatId);
        message.setText("Вы написали: " + messageText + "\n" + showPrivate.getAllInfo(update) + update.hasChannelPost());
        sendMsg(message);
    }

    private void start(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
//            update.getMessage().getFrom();
//            User user = new User();
        message.setChatId(chatId);
        message.setText("Вы написали: " + messageText + "\n" + showPrivate.getAllInfo(update) + update.hasChannelPost());
        sendMsg(message);
    }

    private void help(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
//            update.getMessage().getFrom();
//            User user = new User();
        message.setChatId(chatId);
        message.setText("Вы написали: " + messageText + "\n" + showPrivate.getAllInfo(update) + update.hasChannelPost());
        sendMsg(message);
    }
}

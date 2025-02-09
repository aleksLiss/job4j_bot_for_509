package ru.job4j.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.job4j.bussines.Runner;
import ru.job4j.model.User;
import ru.job4j.repository.sql.Sql2oUserRepository;

@Service
public class TgRemoteService extends TelegramLongPollingBot implements Runner {

    private final String botName;
    private final String botToken;
    private final Sql2oUserRepository sql2oUserRepository;
    public static final Logger LOGGER = LoggerFactory.getLogger(TgRemoteService.class);

    public TgRemoteService(@Value("${telegram.bot.name}") String botName,
                           @Value("${telegram.bot.token}") String botToken,
                           Sql2oUserRepository sql2oUserRepository) {
        this.botName = botName;
        this.botToken = botToken;
        this.sql2oUserRepository = sql2oUserRepository;
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
//                help(update);
                add(update);
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
        try {
            String messageText = update.getMessage().getText();
            User savedUser = new User();
            sql2oUserRepository.save(savedUser);
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Вы написали: " + messageText + "\n");
            sendMsg(message);
        } catch (Exception exception) {
            SendMessage sendErrMsg = new SendMessage();
            sendErrMsg.setText("Ошибка добавления пользователя в базу данных.");
            sendMsg(sendErrMsg);
            exception.printStackTrace();
        }
    }

    private void delete(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
//            update.getMessage().getFrom();
//            User user = new User();
        message.setChatId(chatId);
        message.setText("Вы написали: " + messageText + "\n" + update.hasChannelPost());
        sendMsg(message);
    }

    private void start(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
//            update.getMessage().getFrom();
//            User user = new User();
        message.setChatId(chatId);
        message.setText("Вы написали: " + messageText + "\n" +  update.hasChannelPost());
        sendMsg(message);
    }

    private void help(Update update) {
        try {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Вы написали: " + messageText + "\n");
            sendMsg(message);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}

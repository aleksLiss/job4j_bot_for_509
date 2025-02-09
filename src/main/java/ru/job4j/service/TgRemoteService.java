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

import java.util.Optional;

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
                add(update);
                return;
            case "/delete":
                delete(update);
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
            User savedUser = new User();
            savedUser.setClientId(update.getMessage().getFrom().getId().intValue());
            savedUser.setFirstName(update.getMessage().getFrom().getFirstName());
            savedUser.setLastName(update.getMessage().getFrom().getLastName());
            SendMessage message = new SendMessage();
            Optional<User> res = sql2oUserRepository.save(savedUser);
            if (res.isPresent()) {
                long chatId = update.getMessage().getChatId();
                message.setChatId(chatId);
                message.setText("Пользователь добавлен в базу.");
                sendMsg(message);
                return;
            }
            message = new SendMessage();
            long chatId = update.getMessage().getChatId();
            message.setChatId(chatId);
            message.setText("Такой пользователь уже есть в базе.");
            sendMsg(message);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void delete(Update update) {
        try {
            String messageText = update.getMessage().getText();
            User savedUser = new User();
            savedUser.setClientId(update.getMessage().getFrom().getId().intValue());
            savedUser.setFirstName(update.getMessage().getFrom().getFirstName());
            savedUser.setLastName(update.getMessage().getFrom().getLastName());
            sql2oUserRepository.deleteById(savedUser.getClientId());
            long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Пользователь удален из базы.");
            sendMsg(message);
        } catch (Exception exception) {
            SendMessage sendErrMsg = new SendMessage();
            sendErrMsg.setText("Ошибка добавления пользователя в базу данных.");
            sendMsg(sendErrMsg);
            LOGGER.error(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void start(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
//            update.getMessage().getFrom();
//            User user = new User();
        message.setChatId(chatId);
        message.setText("Вы написали: " + messageText + "\n" + update.hasChannelPost());
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

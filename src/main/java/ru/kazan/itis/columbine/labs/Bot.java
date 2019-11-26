package ru.kazan.itis.columbine.labs;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

class Bot extends TelegramLongPollingBot  {
    private List<Long> users=new ArrayList<>();
    final String hello = "Вас приветствует бот ColumbineLabs!";

    public Bot(DefaultBotOptions options) {
        super(options);
    }

    public static void main(String[] args) {
//        new Properties(Bot.class.getResourceAsStream(""));
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        DefaultBotOptions instance = ApiContext.getInstance(DefaultBotOptions.class);
        instance.setProxyHost("127.0.0.1");
        instance.setProxyPort(9050);
        instance.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        try {
            Bot bot = new Bot(instance);
            telegramBotsApi.registerBot(bot);
            //on action {
            //sendToAllMessage("здесь должно быть сообщение");
            //}
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отправляет всем посетителям сообщение
     * @param message
     */
    public void sendToAllMessage(String message){
        this.users.forEach(user->{
            sendMessage(message, user);
        });
    }

    private void sendMessage(String message, Long user) {
        SendMessage sendMessage = new SendMessage(user, message);
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        if (text.contains("/start")){
            SendMessage sendMessage = new SendMessage(message.getChatId(), text);
            sendMessage.setText(hello);
            users.add(update.getMessage().getChatId());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    public String getBotUsername() {
        return "";
    }

        public String getBotToken() {
            return "";
    }


}

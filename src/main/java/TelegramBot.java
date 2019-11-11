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

class Bot extends TelegramLongPollingBot  {

    public Bot(DefaultBotOptions options) {
        super(options);
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        DefaultBotOptions instance = ApiContext.getInstance(DefaultBotOptions.class);
        instance.setProxyHost("127.0.0.1");
        instance.setProxyPort(9050);
        instance.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        try {
            telegramBotsApi.registerBot(new Bot(instance));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        if (text.contains("/start")){
            SendMessage sendMessage = new SendMessage(message.getChatId(), text);
           sendMessage.setText("Hello Man!");
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

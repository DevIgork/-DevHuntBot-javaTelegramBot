package com.example.telegrambot;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotRegister {
    private final  DevHuntBot devHuntBot;
    public BotRegister(DevHuntBot devHuntBot){
        this.devHuntBot=devHuntBot;
    }
    @PostConstruct
    public void init() throws TelegramApiException {
        TelegramBotsApi  telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(devHuntBot);
    }


}

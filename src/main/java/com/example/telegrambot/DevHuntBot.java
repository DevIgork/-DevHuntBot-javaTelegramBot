package com.example.telegrambot;

import com.example.telegrambot.dto.VacancyDto;
import com.example.telegrambot.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DevHuntBot extends TelegramLongPollingBot {

    public DevHuntBot() {
        super("your bot token");
    }

    @Autowired
    private VacancyService vacancyService;

    private final Map<Long,String> lastShowVacancyLevel = new HashMap<>();


    @Override
    public void onUpdateReceived(Update update) {
        try {
            if(update.getMessage()!=null){
                handleStartCommand(update);
            }
            if (update.getCallbackQuery()!=null){
                String callBackData = update.getCallbackQuery().getData();
                if("showJuniorVacancies".equals(callBackData)){
                    showJuniorVacancies(update);
                }
                 else if("showMiddleVacancies".equals(callBackData)){
                    showMiddleVacancies(update);
                }
                else if("showSeniorVacancies".equals(callBackData)){
                    showSeniorVacancies(update);
                }
                else if(callBackData.startsWith("vacancyId=")){
                    String id = callBackData.split("=")[1];
                    showVacancyDescription(id,update);
                }
                else if("backToVacancies".equals(callBackData)){
                    hadleBackToVacanciesCommand(update);
                }
                else  if("backToStartMenu".equals(callBackData)){
                    hadleBackToStartMenuCommand(update);
                }
            }
        } catch (Exception e){
            throw new RuntimeException("can't send messege to user",e);
        }
    }

    private void hadleBackToStartMenuCommand(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Chose tiitle:");
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setReplyMarkup(getStartMenu());
        execute(sendMessage);
    }


    private void hadleBackToVacanciesCommand(Update update) throws TelegramApiException {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String level = lastShowVacancyLevel.get(chatId);

        if("junior".equals(level)){
            showJuniorVacancies(update);
        }   else if ("middle".equals(level)){
            showMiddleVacancies(update);
        }
        else if(("senior".equals(level))){
            showSeniorVacancies(update);
        }

    }


    private void showVacancyDescription(String id, Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        VacancyDto vacancy = vacancyService.get(id);
        //String description = vacancy.getShortDescriptio();
        //sendMessage.setText(description);
        //sendMessage.setReplyMarkup(getBackToVacancyMenu());
        String fullDesck = "";
        String title = "Title: "+vacancy.getTitle();
        fullDesck+=title+"\n\n";

        String company ="Company: " + vacancy.getCompany();
        fullDesck+=company+"\n\n";

        String shortDescriptio ="Short Descriptio: " + vacancy.getShortDescriptio();
        fullDesck+=shortDescriptio+"\n\n";

        String longtDescriptio = "Description: " + vacancy.getLongDescription();
        fullDesck+=longtDescriptio+"\n\n";

        String salary = "Salary: " + vacancy.getSalary();
        if(salary.contains("-")){
            salary = "Salary: Not specified";
        }
        fullDesck+=salary+"\n\n";

        String link = "Link: " + vacancy.getLink();
        fullDesck+=link+"\n\n";
        sendMessage.setText(fullDesck);
        sendMessage.setReplyMarkup(getBackToVacancyMenu());
        execute(sendMessage);

    }

    private ReplyKeyboard getBackToVacancyMenu(){
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton backToVacancyButtom = new InlineKeyboardButton();
        backToVacancyButtom.setText("Back to vacancies");
        backToVacancyButtom.setCallbackData("backToVacancies");
        row.add(backToVacancyButtom);

        InlineKeyboardButton backToStartMenuButtom = new InlineKeyboardButton();
        backToStartMenuButtom.setText("Back to start menu");
        backToStartMenuButtom.setCallbackData("backToStartMenu");
        row.add(backToStartMenuButtom);

        InlineKeyboardButton getChatGptButtom = new InlineKeyboardButton();
        getChatGptButtom.setText("Get cover letter");
        getChatGptButtom.setUrl("https://chat.openai.com/");
        row.add(getChatGptButtom);

        return new InlineKeyboardMarkup(List.of(row));
    }



    private void showSeniorVacancies(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("please chose vacancy");
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(getSeniorVacanciesMenu());
        execute(sendMessage);

        lastShowVacancyLevel.put(chatId,"senior");
    }

    private ReplyKeyboard getSeniorVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();

        List<VacancyDto> vacancies = vacancyService.getSeniorVacancies();
        for(VacancyDto vacancy: vacancies){
            InlineKeyboardButton vacancyButtom  = new InlineKeyboardButton();
            vacancyButtom.setText(vacancy.getTitle());
            vacancyButtom.setCallbackData("vacancyId=" + vacancy.getId());
            row.add(vacancyButtom);
        }

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));

        return keyboard;
    }

    private void showMiddleVacancies(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("please chose vacancy");
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(getMiddleVacanciesMenu());
        execute(sendMessage);

        lastShowVacancyLevel.put(chatId,"middle");
    }

    private ReplyKeyboard getMiddleVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();

        List<VacancyDto> vacancies = vacancyService.getMiddleVacancies();
        for(VacancyDto vacancy: vacancies){
            InlineKeyboardButton vacancyButtom  = new InlineKeyboardButton();
            vacancyButtom.setText(vacancy.getTitle());
            vacancyButtom.setCallbackData("vacancyId=" + vacancy.getId());
            row.add(vacancyButtom);
        }

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));

        return keyboard;
    }

    private void showJuniorVacancies(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("please chose vacancy");
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(getJuniorVacanciesMenu());
        execute(sendMessage);

        lastShowVacancyLevel.put(chatId,"junior");
    }

    private ReplyKeyboard getJuniorVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();

        List<VacancyDto> vacancies = vacancyService.getJuniorVacancies();
        for(VacancyDto vacancy: vacancies){
            InlineKeyboardButton vacancyButtom  = new InlineKeyboardButton();
            vacancyButtom.setText(vacancy.getTitle());
            vacancyButtom.setCallbackData("vacancyId=" + vacancy.getId());
            row.add(vacancyButtom);
        }

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));

        return keyboard;
    }

    private void handleStartCommand(Update update){
        String text = update.getMessage().getText();
        System.out.println("text resive - "+text);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Welcome to DevHuntBot! Please, chose your title:");
        sendMessage.setReplyMarkup(getStartMenu());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ReplyKeyboard getStartMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton junior = new InlineKeyboardButton();
        junior.setText("junior");
        junior.setCallbackData("showJuniorVacancies");
        row.add(junior);

        InlineKeyboardButton middle = new InlineKeyboardButton();
        middle.setText("Middle");
        middle.setCallbackData("showMiddleVacancies");
        row.add(middle);

        InlineKeyboardButton senior = new InlineKeyboardButton();
        senior.setText("Senior");
        senior.setCallbackData("showSeniorVacancies");
        row.add(senior);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));

        return keyboard;
    }

    @Override
    public String getBotUsername() {
        return "DevHuntingBot";
    }
}

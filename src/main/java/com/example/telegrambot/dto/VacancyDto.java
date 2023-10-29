package com.example.telegrambot.dto;

import com.opencsv.bean.CsvBindByName;

public class VacancyDto {

    @CsvBindByName(column = "Id")
    private String id;
    @CsvBindByName(column = "Title")
    private String title;
    @CsvBindByName(column = "Short description")
    private String shortDescriptio;
    @CsvBindByName(column = "Long description")
    private String longDescription;

    @CsvBindByName(column = "Company")
    private String company;

    @CsvBindByName(column = "Salary")
    private String salary;

    @CsvBindByName(column = "Link")
    private String link;

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getCompany() {
        return company;
    }

    public String getSalary() {
        return salary;
    }

    public String getLink() {
        return link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescriptio() {
        return shortDescriptio;
    }

    public void setShortDescriptio(String shortDescriptio) {
        this.shortDescriptio = shortDescriptio;
    }
}

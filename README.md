# DevHuntBot 🤖

## Description
My first big project, crafted during the Mate Academy in just 4 days, is a Telegram bot named "DevHuntBot." 🚀 This innovative bot offers valuable insights into job vacancies categorized by experience level (junior, middle, and senior). Users can seamlessly select their preferred job level and access detailed vacancy information, including title, company, description, salary, and a link to the job posting. The bot harnesses the power of the Telegram Bot API and the Spring Framework to gracefully handle user interactions and dispatch messages with inline keyboard options. 🛠️

## Project Structure

### 1. `VacancyDto`
   - Data Transfer Object (DTO) capturing the essence of a job vacancy.
   - Features fields for id, title, short description, long description, company, salary, and link. 📋

### 2. `VacancyReaderService`
   - Service dedicated to reading job vacancies from a CSV file (`vacancies.csv`).
   - Leverages the OpenCSV library to adeptly parse CSV data into `VacancyDto` objects. 📄

### 3. `VacancyService`
   - Service orchestrating job vacancy management.
   - Gathers vacancies from the CSV file during application startup, categorizing them into junior, middle, and senior levels.
   - Equipped with methods to retrieve vacancies based on experience level and obtain detailed information about a specific vacancy. 🧑‍💻

### 4. `BotRegister`
   - Component responsible for seamlessly registering the `DevHuntBot` with the Telegram Bot API upon application startup. 🚀

### 5. `DevHuntBot`
   - Principal Telegram bot class extending `TelegramLongPollingBot`.
   - Proficiently handles user interactions, processes commands, and delivers messages with inline keyboard options.
   - Makes effective use of the `VacancyService` to retrieve and present job vacancies. 📬

## How to Run
1. Replace `"your bot token"` in the `DevHuntBot` constructor with your actual Telegram bot token.
2. Build and run the application.

## Commands
- `/start`: Kicks off the conversation, prompting the user to select their job title (junior, middle, or senior). 🚀

## Inline Keyboard Options
- "Junior": Unveils a list of junior-level job vacancies.
- "Middle": Showcases a curated list of middle-level job vacancies.
- "Senior": Highlights a selection of senior-level job opportunities.
- "Back to vacancies": Effortlessly returns to the list of job vacancies for the selected level.
- "Back to start menu": Swiftly returns to the initial menu to choose the job title.
- "Get cover letter": Initiates a link to ChatGPT for the generation of a cover letter. 📝

## Important Links
- [Telegram Bot API](https://core.telegram.org/bots/api)
- [Spring Framework](https://spring.io/)
- [OpenCSV](http://opencsv.sourceforge.net/)

## Note
Prior to running the application, ensure to replace the placeholder `"your bot token"` with your actual Telegram bot token. 🤖

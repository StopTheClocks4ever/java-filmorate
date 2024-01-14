package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class UserValidator {

    public static boolean validate(User user) {
        if(user.getEmail() == null || user.getEmail().isBlank()) {
            log.warn("Адрес электронной почты не может быть пустым.");
            throw new ValidationException("Адрес электронной почты не может быть пустым.");
        }
        if(!user.getEmail().contains("@")) {
            log.warn("Введен неверный адрес электронной почты.");
            throw new ValidationException("Введен неверный адрес электронной почты.");
        }
        if(user.getLogin() == null || user.getLogin().isBlank()) {
            log.warn("Логин не может быть пустым.");
            throw new ValidationException("Логин не может быть пустым.");
        }
        if(user.getLogin().contains(" ")) {
            log.warn("Логин не может содержать пробелы.");
            throw new ValidationException("Логин не может содержать пробелы.");
        }
        if(user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Дата рождения не может быть в будущем.");
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        if(user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return true;
    }
}

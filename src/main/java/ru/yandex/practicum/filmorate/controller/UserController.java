package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final List<User> users = new ArrayList<>();
    private int userId = 0;

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        log.info("Получен запрос POST /users");
        if (UserValidator.validate(user)) {
            user.setId(generateNewId());
            users.add(user);
            log.info("Запрос успешно обработан");
            return user;
        }
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        log.info("Получен запрос PUT /users");
        if (userId < user.getId()) {
            log.warn("Такого пользователя не существует.");
            throw new ValidationException("Такого пользователя не существует.");
        }
        if (UserValidator.validate(user)) {
            users.set(user.getId() - 1, user);
            log.info("Запрос успешно обработан");
            return user;
        }
        return user;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен запрос GET /users");
        return users;
    }

    private int generateNewId() {
        return ++userId;
    }
}

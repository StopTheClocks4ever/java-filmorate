package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 0;

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        log.info("Получен запрос POST /users");
        try {
            if (UserValidator.validate(user)) {
                user.setId(generateNewId());
                users.put(user.getId(), user);
                log.info("Запрос успешно обработан");
            }
        } catch (ValidationException e) {
            log.warn("Пользователь не прошел валидацию");
        }
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody User user) {
        log.info("Получен запрос POST /user");
        try {
            if (UserValidator.validate(user)) {
                users.put(user.getId(), user);
                log.info("Запрос успешно обработан");
            }
        } catch (ValidationException e) {
            log.warn("Пользователь не прошел валидацию");
        }
    }

    @GetMapping("/users")
    public Map<Integer, User> getUsers() {
        log.info("Получен запрос GET /users");
        return users;
    }

    private int generateNewId() {
        return ++userId;
    }
}

package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final UserService userService = new UserService();

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        log.info("Получен запрос POST /users");
        if (UserValidator.validate(user)) {
            userService.addUser(user);
            log.info("Запрос успешно обработан");
            return user;
        }
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        log.info("Получен запрос PUT /users");
        if (userService.getCurrentUserId() < user.getId()) {
            log.warn("Такого пользователя не существует.");
            throw new ValidationException("Такого пользователя не существует.");
        }
        if (UserValidator.validate(user)) {
            userService.updateUser(user);
            log.info("Запрос успешно обработан");
            return user;
        }
        return user;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен запрос GET /users");
        return userService.getUsers();
    }
}

package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final InMemoryUserStorage inMemoryUserStorage;
    private final UserService userService;

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        log.info("Получен запрос POST /users");
        if (UserValidator.validate(user)) {
            inMemoryUserStorage.addUser(user);
            log.info("Запрос успешно обработан");
            return user;
        }
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        log.info("Получен запрос PUT /users");
        if (!inMemoryUserStorage.isUserExists(user)) {
            log.warn("Такого пользователя не существует.");
            throw new UserNotFoundException("Такого пользователя не существует.");
        }
        if (UserValidator.validate(user)) {
            inMemoryUserStorage.updateUser(user);
            log.info("Запрос успешно обработан");
            return user;
        }
        return user;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен запрос GET /users");
        return new ArrayList<>(inMemoryUserStorage.getUsers().values());
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        if (!inMemoryUserStorage.getUsers().containsKey(id)) {
            log.warn("Такого пользователя не существует.");
            throw new UserNotFoundException("Такого пользователя не существует.");
        }
        log.info("Получен запрос GET /users");
        return inMemoryUserStorage.getUsers().get(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getUserFriends(@PathVariable long id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getUserCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info("Получен запрос GET /users/{id}/friends/common/{otherId}");
        return userService.commonFriends(id, otherId);
    }
}

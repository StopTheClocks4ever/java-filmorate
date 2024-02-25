package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;
    private final UserService service;

    public UserController(@Qualifier("userDbStorage") UserStorage userStorage, UserService service) {
        this.userStorage = userStorage;
        this.service = service;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        log.info("Получен запрос POST /users");
        if (UserValidator.validate(user)) {
            userStorage.addUser(user);
            log.info("Запрос успешно обработан");
            return user;
        }
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        log.info("Получен запрос PUT /users");
        List<User> users = getUsers();
        Map<Integer, User> usersAndId = new HashMap<>();
        for (User checkUser: users) {
            usersAndId.put(checkUser.getId(), checkUser);
        }
        if (usersAndId.containsKey(user.getId())) {
            if (UserValidator.validate(user)) {
                userStorage.updateUser(user);
                log.info("Запрос успешно обработан");
                return user;
            }
            return user;
        }
        else {
            throw new UserNotFoundException("Такого пользователя не существует.");
        }
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Получен запрос GET /users");
        return new ArrayList<>(userStorage.getUsers());
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Получен запрос GET /users/{id}");
        User user = userStorage.getUserById(id);
        if (user != null) {
            return user;
        } else {
            log.warn("Такого пользователя не существует.");
            throw new UserNotFoundException("Такого пользователя не существует.");
        }
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        User user1 = getUserById(id);
        User user2 = getUserById(friendId);
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        User user1 = getUserById(id);
        User user2 = getUserById(friendId);
        service.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getUserFriends(@PathVariable int id) {
        User user1 = getUserById(id);
        return service.getUserFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getUserCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен запрос GET /users/{id}/friends/common/{otherId}");
        User user1 = getUserById(id);
        User user2 = getUserById(otherId);
        return service.commonFriends(id, otherId);
    }
}

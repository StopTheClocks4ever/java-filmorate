package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final List<User> users = new ArrayList<>();
    private int userId = 0;

    private int generateNewId() {
        return ++userId;
    }

    public int getCurrentUserId() {
        return userId;
    }

    public void addUser(User user) {
        user.setId(generateNewId());
        users.add(user);
    }

    public void updateUser(User user) {
        users.set(user.getId() - 1, user);
    }

    public List<User> getUsers() {
        return users;
    }
}

package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 0;

    private int generateNewId() {
        return ++userId;
    }

    public void addUser(User user) {
        user.setId(generateNewId());
        users.put(user.getId(), user);
    }

    public void updateUser(User user) {
        users.put(user.getId(), user);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public boolean isUserExists(User user){
        return users.containsKey(user.getId());
    }
}

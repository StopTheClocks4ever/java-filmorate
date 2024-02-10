package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(int userId);

    Map<Long, User> getUsers();
}

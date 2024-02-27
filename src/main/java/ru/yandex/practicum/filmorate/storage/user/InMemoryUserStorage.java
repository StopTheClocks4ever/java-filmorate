package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final List<User> users = new ArrayList<>();
    private int userId = 0;

    private int generateNewId() {
        return ++userId;
    }

    public void addUser(User user) {

    }

    public void updateUser(User user) {

    }

    public void deleteUser(int userId) {
        users.remove(userId);
    }

    public List<User> getUsers() {
        return users;
    }

    public boolean isUserExists(User user) {
        return users.contains(user);
    }

    public User getUserById(int id) {
        List<User> users = getUsers();
        Map<Integer, User> usersAndId = new HashMap<>();
        for (User user: users) {
            usersAndId.put(user.getId(), user);
        }
        if (usersAndId.containsKey(id)) {
            return usersAndId.get(id);
        }
        else {
            throw new UserNotFoundException("Такого пользователя не существует.");
        }
    }

    public void addFriend(int id, int friendId) {

    }

    public void deleteFriend(int id, int friendId) {

    }

    public List<User> getUserFriends(int id) {
        return null;
    }

    @Override
    public List<User> getUserCommonFriends(int id, int otherId) {
        return null;
    }
}

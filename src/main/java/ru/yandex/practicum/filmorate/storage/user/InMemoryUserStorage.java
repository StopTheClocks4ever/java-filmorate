package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private int userId = 0;

    private int generateNewId() {
        return ++userId;
    }

    public void addUser(User user) {
        user.setId(generateNewId());
        user.setFriends(new HashSet<>());
        users.put(user.getId(), user);
    }

    public void updateUser(User user) {
        user.setFriends(new HashSet<>());
        users.put(user.getId(), user);
    }

    public void deleteUser(int userId) {
        users.remove(userId);
    }

    public Map<Long, User> getUsers() {
        return users;
    }

    public boolean isUserExists(User user) {
        return users.containsKey(user.getId());
    }
}

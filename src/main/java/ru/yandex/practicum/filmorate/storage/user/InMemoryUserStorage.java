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
        user.setId(generateNewId());
        user.setFriends(new HashSet<>());
        users.add(user);
    }

    public void updateUser(User user) {
        user.setFriends(new HashSet<>());
        users.add(user);
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
        users.get(id).getFriends().add(friendId);
        updateUser(getUserById(id));
        users.get(friendId).getFriends().add(id);
        updateUser(getUserById(friendId));
    }

    public void deleteFriend(int id, int friendId) {
        users.get(id).getFriends().remove(friendId);
        updateUser(getUserById(id));
        users.get(friendId).getFriends().remove(id);
        updateUser(getUserById(friendId));
    }

    public List<User> getUserFriends(int id) {
        Set<Integer> userFriends = new HashSet<>(users.get(id).getFriends());
        List<User> friends = new ArrayList<>();
        for (int friendId : userFriends) {
            friends.add(users.get(friendId));
        }
        return friends;
    }

    @Override
    public List<User> getUserCommonFriends(int id, int otherId) {
        Set<Integer> user1Friends = new HashSet<>(users.get(id).getFriends());
        Set<Integer> user2Friends = users.get(otherId).getFriends();
        user1Friends.retainAll(user2Friends);

        List<User> commonFriends = new ArrayList<>();
        for (int userId : user1Friends) {
            commonFriends.add(users.get(userId));
        }
        return commonFriends;
    }

}

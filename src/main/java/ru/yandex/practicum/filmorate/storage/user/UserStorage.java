package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(int userId);

    List<User> getUsers();

    User getUserById(int id);

    void addFriend(int id, int friendId);

    void deleteFriend(int id, int friendId);

    List<User> getUserFriends(int id);

    List<User> getUserCommonFriends(int id, int otherId);
}

package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Qualifier("userDbStorage")
public class UserService {

    private final UserStorage userStorage;

    public UserService (@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }
    public void addFriend(int id, int friendId) {
        userStorage.addFriend(id, friendId);
    }

    public void deleteFriend(int id, int friendId) {
        userStorage.deleteFriend(id, friendId);
    }

    public List<User> getUserFriends(int id) {
        return userStorage.getUserFriends(id);
    }

    public List<User> commonFriends(int id, int otherId) {

        return userStorage.getUserCommonFriends(id, otherId);
    }
}

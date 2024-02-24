package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage inMemoryUserStorage;

    public void addFriend(long userId1, long userId2) {
        if (!(inMemoryUserStorage.getUsers().containsKey(userId1)) || !(inMemoryUserStorage.getUsers().containsKey(userId2))) {
            throw new UserNotFoundException("Указанный пользователь не найден");
        }
        inMemoryUserStorage.getUsers().get(userId1).getFriends().add(userId2);
        inMemoryUserStorage.getUsers().get(userId2).getFriends().add(userId1);
    }

    public void deleteFriend(long userId1, long userId2) {
        if (!(inMemoryUserStorage.getUsers().containsKey(userId1)) || !(inMemoryUserStorage.getUsers().containsKey(userId2))) {
            throw new UserNotFoundException("Указанный пользователь не найден");
        }
        inMemoryUserStorage.getUsers().get(userId1).getFriends().remove(userId2);
        inMemoryUserStorage.getUsers().get(userId2).getFriends().remove(userId1);
    }

    public List<User> getUserFriends(long userId) {
        if (!(inMemoryUserStorage.getUsers().containsKey(userId))) {
            throw new UserNotFoundException("Указанный пользователь не найден");
        }
        Set<Long> userFriends = new HashSet<>(inMemoryUserStorage.getUsers().get(userId).getFriends());
        List<User> friends = new ArrayList<>();
        for (long id : userFriends) {
            friends.add(inMemoryUserStorage.getUsers().get(id));
        }
        return friends;
    }

    public List<User> commonFriends(long userId1, long userId2) {
        if (!(inMemoryUserStorage.getUsers().containsKey(userId1)) || !(inMemoryUserStorage.getUsers().containsKey(userId2))) {
            throw new UserNotFoundException("Указанный пользователь не найден");
        }
        Set<Long> user1Friends = new HashSet<>(inMemoryUserStorage.getUsers().get(userId1).getFriends());
        Set<Long> user2Friends = inMemoryUserStorage.getUsers().get(userId2).getFriends();
        user1Friends.retainAll(user2Friends);

        List<User> commonFriends = new ArrayList<>();
        for (long userId : user1Friends) {
            commonFriends.add(inMemoryUserStorage.getUsers().get(userId));
        }
        return commonFriends;
    }
}

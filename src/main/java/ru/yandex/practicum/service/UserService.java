package ru.yandex.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exceptions.NotFoundException;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User createUser(User user) {
        checksUser(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        checksUser(user);
        return userStorage.updateUser(user);
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public User addFriend(int userId, int friendId) {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException("Данный" + userId + "," + friendId + "не найден");
        }
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriendIds().add(friendId);
        friend.getFriendIds().add(userId);
        updateUser(user);
        updateUser(friend);
        return user;
    }

    public User removeFriend(int userId, int friendId) {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException("Данный" + userId + "," + friendId + "не найден");
        }
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriendIds().remove(friendId);
        friend.getFriendIds().remove(userId);
        updateUser(user);
        updateUser(friend);
        return user;
    }

    public List<User> getFriendsList(int userId) {
        if (userId < 0) {
            throw new NotFoundException("Данный" + userId + "не найден");
        }
        User user = userStorage.getUserById(userId);
        List<User> friendsList = new ArrayList<>();
        for (Integer id : user.getFriendIds()) {
            friendsList.add(userStorage.getUserById(id));
        }
        return friendsList;
    }

    public List<User> getMutualFriends(int userId, int friendId) {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException("Данный" + userId + "," + friendId + "не найден");
        }
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        Set<Integer> userFriends = user.getFriendIds();
        Set<Integer> friendFriends = friend.getFriendIds();
        return userFriends.stream()
                .filter(friendFriends::contains)
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    private void checksUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Адрес электронной почты не может быть пустым.");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
    }
}

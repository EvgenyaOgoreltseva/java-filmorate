package ru.yandex.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exceptions.NotFoundException;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        checksUser(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        checksUser(user);
        return userStorage.updateUser(user);
    }

    public User getUserById(int id) {
        if (id < 0) {
            throw new NotFoundException("Пользователь не найден");
        }
        return userStorage.getUserById(id);
    }

    public User addFriend(int userId, int friendId) {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException("Пользователь с id " + userId + " или пользователь с id " + friendId + " не найдены.");
        }
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriendIds().add(friendId);
        friend.getFriendIds().add(userId);
        updateUser(user);
        updateUser(friend);
        log.info("Пользователь с id " + userId + " добавил в друзья пользователя с id " + friendId);
        return user;
    }

    public User removeFriend(int userId, int friendId) {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException("Пользователь с id " + userId + " или пользователь с id " + friendId + " не найдены.");
        }
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriendIds().remove(friendId);
        friend.getFriendIds().remove(userId);
        updateUser(user);
        updateUser(friend);
        log.info("Пользователь с id " + userId + " удалил из друзей пользователя с id " + friendId);
        return user;
    }

    public List<User> getFriendsList(int userId) {
        if (userId < 0) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден.");
        }
        User user = userStorage.getUserById(userId);
        List<User> friendsList = new ArrayList<>();
        for (Integer id : user.getFriendIds()) {
            friendsList.add(userStorage.getUserById(id));
        }
        log.info("Получен список друзей пользователя " + user.getName());
        return friendsList;
    }

    public List<User> getMutualFriends(int userId, int friendId) {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException("Пользователь с id " + userId + " или пользователь с id " + friendId + " не найдены.");
        }
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        Set<Integer> userFriends = user.getFriendIds();
        Set<Integer> friendFriends = friend.getFriendIds();
        log.info("Получен список общих друзей пользователей с id " + userId + " и " + friendId);
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
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
    }
}

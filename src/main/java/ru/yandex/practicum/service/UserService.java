package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dao.user.UserDao;
import ru.yandex.practicum.exceptions.NotFoundException;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.User;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserDao userDao;

    public List<User> findAllUsers() {
        log.info("Получен список всех пользователей");
        return userDao.findAllUsers();
    }

    public User createUser(User user) {
        checksUser(user);
        log.info("Пользователь сохранен");
        return userDao.createUser(user);
    }

    public User updateUser(User user) {
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        checksUser(user);
        log.info("Данные пользователя обновлены");
        return userDao.updateUser(user);
    }

    public int deleteUser(int id) {
        log.info("Пользователь удален");
        return userDao.deleteUser(id);
    }

    public User getUserById(int id) {
        if (id < 0) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        log.info("Получен пользователь с id " + id);
        return userDao.getUserById(id);
    }

    public void addFriend(int userId, int friendId) {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException("Пользователь с id " + userId + " или " + friendId + " не найден");
        }

        log.info("Пользователь " + userId + " добавлен в список друзей " + friendId);
        userDao.addFriend(userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException("Пользователь с id " + userId + " или " + friendId + " не найден");
        }

        log.info("Пользователь " + userId + " удален из списка друзей " + friendId);
        userDao.removeFriend(userId, friendId);
    }

    public List<User> getFriendsList(int userId) {
        if (userId < 0) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }
        log.info("Список друзей пользователя " + userId);
        return userDao.getFriendsList(userId);
    }

    public List<User> getMutualFriends(int userId, int friendId) {
        if (userId < 0 || friendId < 0) {
            throw new NotFoundException("Пользователь с id " + userId + " или " + friendId + " не найден");
        }
        log.info("Получен список общих друзей пользователя " + userId + " и пользователя " + friendId + ".");
        return userDao.getMutualFriends(userId, friendId);
    }

    private void checksUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Адрес электронной почты не может быть пустым.");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
    }
}


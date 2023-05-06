package ru.yandex.practicum.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private int id = 1;
    private final Map<Integer, User> users = new HashMap<>();

    private int generateId() {
        return id++;
    }

    @GetMapping
    public ArrayList<User> findAll() {
        log.info("Список пользователей получен.");
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@RequestBody User user) {
        checksUser(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Пользователь сохранен");
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        checksUser(user);
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователя с id " + user.getId() + " не найдено");
        }
        users.put(user.getId(), user);
        log.info("Пользователь с id " + user.getId() + " обновлен.");
        return user;
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

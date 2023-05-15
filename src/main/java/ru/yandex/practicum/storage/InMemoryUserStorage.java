package ru.yandex.practicum.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceptions.NotFoundException;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer,User> users = new HashMap<>();
    private int id = 1 ;
    private int generateId(){
        return id++;
    }

    @Override
    public List<User> findAllUsers() {
        log.info("Список пользователей получен.");
        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Пользователь сохранен");
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователя с id " + user.getId() + " не найдено");
        }
        users.put(user.getId(), user);
        log.info("Пользователь с id " + user.getId() + " обновлен.");
        return user;
    }

    @Override
    public User getUserById(int id) {
        if (!users.containsKey(id)){
            throw new NotFoundException("Пользователя с id " + id + " не найдено");
        }
        log.info("Пользователь с id " + id + " найден.");
        return users.get(id);
    }
}

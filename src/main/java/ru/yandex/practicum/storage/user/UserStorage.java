package ru.yandex.practicum.storage.user;

import ru.yandex.practicum.model.User;

import java.util.List;


public interface UserStorage {
    List<User> findAllUsers();

    User createUser(User user);

    User updateUser(User user);

    User getUserById(int id);
}

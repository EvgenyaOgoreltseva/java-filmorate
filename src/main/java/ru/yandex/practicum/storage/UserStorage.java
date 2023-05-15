package ru.yandex.practicum.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.User;

import java.util.List;

@Component
public interface UserStorage {
    List<User> findAllUsers();

    User createUser(User user);

    User updateUser(User user);

    User getUserById(int id);
}

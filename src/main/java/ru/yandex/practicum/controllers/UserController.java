package ru.yandex.practicum.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> findAllUsers() {
        log.info("Получен запрос на получение списка всех пользователей");
        return userService.findAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("Получен запрос на добавление пользователя");
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("Получен запрос на обновление пользователя с ID - " + user.getId());
        return userService.updateUser(user);
    }

    @DeleteMapping
    public int deleteUser(@RequestBody int userId) {
        log.info("Получен запрос на удаление пользователя с ID - " + userId);
        return userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        log.info("Получен запрос на получение пользователя с ID - " + userId);
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable("userId") int userId, @PathVariable("friendId") int friendId) {
        log.info("Получен запрос на добавление друга с ID - " + friendId + " в друзья к пользователю с ID - " + userId);
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void removeFriend(@PathVariable("userId") int userId, @PathVariable("friendId") int friendId) {
        log.info("Получен запрос на удаление друга с ID - " + friendId + " из друзей пользователя с ID - " + userId);
        userService.removeFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriendsList(@PathVariable int userId) {
        log.info("Получен запрос на получение списка друзей пользователя с ID - " + userId);
        return userService.getFriendsList(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable int userId, @PathVariable int otherId) {
        log.info("Получен запрос на получение списка общих друзей пользователей с ID - " + userId + " и " + otherId);
        return userService.getMutualFriends(userId, otherId);
    }
}

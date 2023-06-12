package ru.yandex.practicum.controllers;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.FilmorateApplication;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTests {

    private final UserController userController;

    @Test
    void findAllAddedUserInTheList() {
        User user1 = new User(1, "dmitry@gmail.com", "dmitry", "Дмитрий", LocalDate.of(1980, 6, 6));
        User user2 = new User(2, "ivan@gmail.com", "ivan", "Иван", LocalDate.of(1990, 5, 5));
        userController.createUser(user1);
        userController.createUser(user2);

        List<User> result = userController.findAllUsers();

        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    }

    @Test
    void createValidUser() {
        User user = new User(1, "ivan@gmail.com", "ivan", "Иван", LocalDate.of(1990, 6, 6));

        User result = userController.createUser(user);

        assertEquals(user, result);

    }

    @Test
    void createUserWithBlankOrInvalidEmail() {
        User user1 = new User(1, "dmitry-gmail.com", "dmitry", "Дмитрий", LocalDate.of(1990, 6, 6));
        User user2 = new User(2, null, "ivan", "Иван", LocalDate.of(1980, 5, 5));

        assertThrows(ValidationException.class, () -> userController.createUser(user1));
        assertThrows(ValidationException.class, () -> userController.createUser(user2));

    }

    @Test
    void createUserWithInvalidLogin() {
        User user1 = new User(1, "dmitry@gmail.com", null, "Дмитрий", LocalDate.of(1990, 5, 5));
        User user2 = new User(2, "ivan@gmail.com", "iva n", "Иван", LocalDate.of(1980, 6, 6));

        assertThrows(ValidationException.class, () -> userController.createUser(user1));
        assertThrows(ValidationException.class, () -> userController.createUser(user2));

    }

    @Test
    void createUserWithInvalidBirthday() {
        User user2 = new User(1, "ivan@gmail.com", "ivan", "Иван", LocalDate.of(2023, 12, 1));
        assertThrows(ValidationException.class, () -> userController.createUser(user2));

    }

    @Test
    void createUserWithBlankOrNoName() {
        User user1 = new User(1, "dmitry@gmail.com", "dmitry", "", LocalDate.of(1990, 6, 6));

        userController.createUser(user1);

        assertEquals(user1.getName(), user1.getLogin());

    }

    @Test
    void updateExistingUserWithValidUser() {
        User user1 = new User(1, "dmitry@gmail.com", "dmitry", "Дмитрий", LocalDate.of(1980, 6, 6));
        userController.createUser(user1);

        User user2 = new User(1, "ivan@gmail.com", "ivan", "Иван", LocalDate.of(1990, 5, 5));
        User result = userController.updateUser(user2);

        assertEquals(result, user2);

    }

    @Test
    void updateExistingUserWithBlankOrInvalidEmail() {
        User user1 = new User(1, "dmitry@gmail.com", "dmitry", "Дмитрий", LocalDate.of(1990, 6, 6));
        userController.createUser(user1);

        User user2 = new User(1, null, "ivan", "Иван", LocalDate.of(1980, 5, 5));
        assertThrows(ValidationException.class, () -> userController.updateUser(user2));
    }

    @Test
    void updateExistingUserWithInvalidLogin() {
        User user1 = new User(1, "dmitry@gmail.com", "dmitry", "Дмитрий", LocalDate.of(1990, 6, 6));
        userController.createUser(user1);

        User user2 = new User(1, "Ivan@gmail.com", "iva n", "Иван", LocalDate.of(1980, 5, 5));
        assertThrows(ValidationException.class, () -> userController.updateUser(user2));
    }

    @Test
    void updateExistingUserWithInvalidBirthday() {
        User user1 = new User(1, "dmitry@gmail.com", "dmitry", "Дмитрий", LocalDate.of(1990, 6, 6));
        userController.createUser(user1);

        User user2 = new User(1, "Ivan@gmail.com", "ivan", "Иван", LocalDate.of(2023, 12, 4));
        assertThrows(ValidationException.class, () -> userController.updateUser(user2));
    }

    @Test
    void updateExistingUserWithBlankOrNoName() {
        User user1 = new User(1, "dmitry@gmail.com", "dmitry", "Дмитрий", LocalDate.of(1990, 6, 6));
        userController.createUser(user1);

        User user2 = new User(1, "Ivan@gmail.com", "ivan", "", LocalDate.of(2022, 6, 4));
        userController.updateUser(user2);

        assertEquals(user2.getName(), user2.getLogin());
    }

}

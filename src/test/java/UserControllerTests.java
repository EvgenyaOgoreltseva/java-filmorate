import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.controllers.UserController;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTests {

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void testFindAll() {
        User user1 = new User(1, "dmitry@gmail.com", "dmitry", "Дмитрий", LocalDate.of(1980, 6, 6));
        User user2 = new User(2, "ivan@gmail.com", "ivan", "Иван", LocalDate.of(1990, 5, 5));
        userController.create(user1);
        userController.create(user2);

        Collection<User> result = userController.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    }

    @Test
    void testCreateValidUser() {
        User user = new User(1, "ivan@gmail.com", "ivan", "Иван", LocalDate.of(1990, 6, 6));

        User result = userController.create(user);

        assertEquals(user, result);

    }

    @Test
    void testCreateUserWithBlankOrInvalidEmail() {
        User user1 = new User(1, "dmitry-gmail.com", "dmitry", "Дмитрий", LocalDate.of(1990, 6, 6));
        User user2 = new User(2, null, "ivan", "Иван", LocalDate.of(1980, 5, 5));

        assertThrows(ValidationException.class, () -> userController.create(user1));
        assertThrows(ValidationException.class, () -> userController.create(user2));

    }

    @Test
    void testCreateUserWithBlankOrInvalidLogin() {
        User user1 = new User(1, "dmitry@gmail.com", null, "Дмитрий", LocalDate.of(1990, 5, 5));
        User user2 = new User(2, "ivan@gmail.com", "iva n", "Иван", LocalDate.of(1980, 6, 6));

        assertThrows(ValidationException.class, () -> userController.create(user1));
        assertThrows(ValidationException.class, () -> userController.create(user2));

    }

    @Test
    void testCreateUserWithInvalidBirthday() {
        User user2 = new User(1, "ivan@gmail.com", "ivan", "Иван", LocalDate.of(2023, 12, 1));
        assertThrows(ValidationException.class, () -> userController.create(user2));

    }

    @Test
    void testCreateUserWithBlankOrNoName() {
        User user1 = new User(1, "dmitry@gmail.com", "dmitry", "", LocalDate.of(1990, 6, 6));

        userController.create(user1);

        assertEquals(user1.getName(), user1.getLogin());

    }

}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.controllers.FilmController;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.Film;
import java.time.LocalDate;
import java.util.Collection;


import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTests {

    private FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
    }

    @Test
    void testFindAll() {
        Film film1 = new Film(1, "Film 1", "Description 1", LocalDate.now(), 120);
        Film film2 = new Film(2, "Film 2", "Description 2", LocalDate.now(), 150);
        filmController.create(film1);
        filmController.create(film2);

        Collection<Film> result = filmController.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(film1));
        assertTrue(result.contains(film2));
    }

    @Test
    void testCreateValidFilm() {
        Film film = new Film(1, "Film Name", "Film Description",
                LocalDate.of(2023, 4, 29), 120);

        Film result = filmController.create(film);

        assertEquals(film, result);

    }

    @Test
    void testCreateFilmWithNoName() {
        Film film = new Film(1, null, "Film Description",
                LocalDate.of(2023, 4, 29), 120);

        assertThrows(ValidationException.class, () -> filmController.create(film));


    }

        @Test
    void testFilmWithDateBeforeThanRequired() {
        Film film = new Film(1, "John Wick", "A movie about a man",
                LocalDate.of(1895, 12, 27), 120);

        assertThrows(ValidationException.class, () -> filmController.create(film));


    }
    @Test
    void testFilmWithDescriptionMoreThan200Words() {
        Film film = new Film(1, "Shadows Of Realms", """
                "In a world on the brink of chaos, a reluctant hero emerges to confront the forces of darkness in 'Shadows of Destiny.'\\n"
                        + "Set in a post-apocalyptic landscape, the film follows the journey of Alex Turner, a former soldier haunted by his past.\\n"
                        + "When an ancient prophecy resurfaces, Alex finds himself thrust into a battle against a malevolent sorcerer who seeks to unleash ultimate destruction upon the world.\\n\\n"
                        + "As he embarks on this perilous quest, Alex is joined by a diverse group of companions, each harboring their own secrets and hidden powers.\\n"
                        + "Together, they must navigate treacherous landscapes, face formidable enemies, and uncover long-lost relics that hold the key to saving humanity.\\n\\n"
                        + "'Shadows of Destiny' is an epic fantasy adventure that seamlessly blends elements of magic, suspense, and heart-pounding action.\\n"
                        + "With stunning visual effects, captivating performances, and a gripping storyline, the film immerses audiences into a richly imagined world where the fate of mankind hangs in the balance.\\n"
                        + "Prepare to be spellbound as the battle between light and darkness unfolds in this thrilling cinematic experience.")""",
                LocalDate.of(2023, 4, 29), 120);

        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void testFilmWithInvalidDuration() {
        Film film = new Film(1, "Scooby-Doo", "Funny Movie",
                LocalDate.of(2023, 4, 29), -120);

        assertThrows(ValidationException.class, () -> filmController.create(film));
    }
}



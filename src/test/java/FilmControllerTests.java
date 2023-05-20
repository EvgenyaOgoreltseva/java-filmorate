import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.controllers.FilmController;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.Film;

import ru.yandex.practicum.service.FilmService;
import ru.yandex.practicum.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTests {

    private FilmController filmController;
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        filmService = new FilmService(new InMemoryFilmStorage());
        filmController = new FilmController(filmService);
    }

    @Test
    void findAllFilmsAddedToList() {
        Film film1 = new Film(1, "Film 1", "Description 1", LocalDate.now(), 120);
        Film film2 = new Film(2, "Film 2", "Description 2", LocalDate.now(), 150);
        filmController.createFilm(film1);
        filmController.createFilm(film2);

        List<Film> result = filmController.findAllFilms();

        assertEquals(2, result.size());
        assertTrue(result.contains(film1));
        assertTrue(result.contains(film2));
    }

    @Test
    void createValidFilm() {
        Film film = new Film(1, "Film Name", "Film Description", LocalDate.of(2023, 4, 29), 120);

        Film result = filmController.createFilm(film);

        assertEquals(film, result);

    }

    @Test
    void createFilmWithNoName() {
        Film film = new Film(1, null, "Film Description", LocalDate.of(2023, 4, 29), 120);

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));

    }

    @Test
    void createFilmWithDateBeforeThanRequired() {
        Film film = new Film(1, "John Wick", "A movie about a man",
                LocalDate.of(1895, 12, 27), 120);

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));


    }

    @Test
    void createFilmWithDescriptionMoreThanRequiredWords() {
        Film film = new Film(1, "Shadows Of Realms",
                "In a world on the brink of chaos, a reluctant hero emerges to confront the forces of darkness in 'Shadows of Destiny.'\\n"
                        + "Set in a post-apocalyptic landscape, the film follows the journey of Alex Turner, a former soldier haunted by his past.\\n"
                        + "When an ancient prophecy resurfaces, Alex finds himself thrust into a battle against a malevolent sorcerer who seeks to unleash ultimate destruction upon the world.\\n\\n"
                        + "As he embarks on this perilous quest, Alex is joined by a diverse group of companions, each harboring their own secrets and hidden powers.\\n"
                        + "Together, they must navigate treacherous landscapes, face formidable enemies, and uncover long-lost relics that hold the key to saving humanity.\\n\\n"
                        + "'Shadows of Destiny' is an epic fantasy adventure that seamlessly blends elements of magic, suspense, and heart-pounding action.\\n"
                        + "With stunning visual effects, captivating performances, and a gripping storyline, the film immerses audiences into a richly imagined world where the fate of mankind hangs in the balance.\\n"
                        + "Prepare to be spellbound as the battle between light and darkness unfolds in this thrilling cinematic experience.",
                LocalDate.of(2023, 4, 29), 120);

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    void createFilmWithInvalidDuration() {
        Film film = new Film(1, "Scooby-Doo", "Funny Movie",
                LocalDate.of(2023, 4, 29), -120);

        assertThrows(ValidationException.class, () -> filmController.createFilm(film));
    }

    @Test
    void updateExistingFilmWithValidFilm() {

        Film film = new Film(1, "Film Name", "Film Description", LocalDate.of(2023, 4, 29), 120);
        filmController.createFilm(film);

        Film film1 = new Film(1, "Scooby Doo", "A Scooby Doo Movie", LocalDate.of(2021, 3, 23), 100);
        Film result = filmController.updateFilm(film1);

        assertEquals(result, film1);

    }

    @Test
    void updateExistingFilmWithNoName() {
        Film film = new Film(1, "Film Name", "Film Description", LocalDate.of(2023, 4, 29), 120);
        filmController.createFilm(film);

        Film film1 = new Film(1, null, "A Scooby Doo Movie", LocalDate.of(2021, 3, 23), 100);
        assertThrows(ValidationException.class, () -> filmController.updateFilm(film1));

    }

    @Test
    void updateExistingFilmWithInvalidDate() {
        Film film = new Film(1, "Film Name", "Film Description", LocalDate.of(2023, 4, 29), 120);
        filmController.createFilm(film);

        Film film1 = new Film(1, "Scooby Doo", "A Scooby Doo Movie", LocalDate.of(1895, 12, 25), 100);
        assertThrows(ValidationException.class, () -> filmController.updateFilm(film1));

    }

    @Test
    void updateExistingFilmWithDescriptionMoreThanRequiredWords() {
        Film film = new Film(1, "Film Name", "Film Description", LocalDate.of(2023, 4, 29), 120);
        filmController.createFilm(film);

        Film film1 = new Film(1, "Shadows Of Realms",
                "In a world on the brink of chaos, a reluctant hero emerges to confront the forces of darkness in 'Shadows of Destiny.'\\n"
                        + "Set in a post-apocalyptic landscape, the film follows the journey of Alex Turner, a former soldier haunted by his past.\\n"
                        + "When an ancient prophecy resurfaces, Alex finds himself thrust into a battle against a malevolent sorcerer who seeks to unleash ultimate destruction upon the world.\\n\\n"
                        + "As he embarks on this perilous quest, Alex is joined by a diverse group of companions, each harboring their own secrets and hidden powers.\\n"
                        + "Together, they must navigate treacherous landscapes, face formidable enemies, and uncover long-lost relics that hold the key to saving humanity.\\n\\n"
                        + "'Shadows of Destiny' is an epic fantasy adventure that seamlessly blends elements of magic, suspense, and heart-pounding action.\\n"
                        + "With stunning visual effects, captivating performances, and a gripping storyline, the film immerses audiences into a richly imagined world where the fate of mankind hangs in the balance.\\n"
                        + "Prepare to be spellbound as the battle between light and darkness unfolds in this thrilling cinematic experience.",
                LocalDate.of(2023, 4, 29), 120);
        assertThrows(ValidationException.class, () -> filmController.updateFilm(film1));

    }

    @Test
    void updateExistingFilmWithInvalidDuration() {
        Film film = new Film(1, "Film Name", "Film Description", LocalDate.of(2023, 4, 29), 120);
        filmController.createFilm(film);

        Film film1 = new Film(1, "Scooby Doo", "A Scooby Doo Movie", LocalDate.of(1990, 12, 25), -100);
        assertThrows(ValidationException.class, () -> filmController.updateFilm(film1));

    }

}



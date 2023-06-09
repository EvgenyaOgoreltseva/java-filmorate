package ru.yandex.practicum.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.dao.film.FilmDao;
import ru.yandex.practicum.dao.user.UserDao;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.Mpa;
import ru.yandex.practicum.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FilmDaoTests {

    private final FilmDao filmDao;
    private final UserDao userDao;

    @Test
    void createFilm() {

        Film film = new Film(1, "John Wick", "A Film about a man",
                LocalDate.of(2012, 9, 8), 172, new Mpa(4, "R"));
        filmDao.createFilm(film);

        assertEquals(1, filmDao.findAllFilms().size());

        assertEquals("R", filmDao.getFilmById(1).getMpa().getName());
        assertEquals("John Wick", filmDao.getFilmById(1).getName());
        assertEquals(172, filmDao.getFilmById(1).getDuration());

    }

    @Test
    void updateFilm() {

        Film film = new Film(1, "Forrest Gump", "Film description",
                LocalDate.of(1994, 6, 23), 136, new Mpa(3, "PG-13"));
        filmDao.createFilm(film);
        Film film2 = new Film(1, "Inception", "Film description",
                LocalDate.of(2016, 10, 8), 121, new Mpa(4, "R"));

        filmDao.updateFilm(film2);

        assertEquals("R", filmDao.getFilmById(1).getMpa().getName());
        assertEquals("Inception", filmDao.getFilmById(1).getName());
        assertEquals(121, filmDao.getFilmById(1).getDuration());
    }

    @Test
    void getFilmByIdFromDatabase() {

        Film film1 = new Film(1, "Forrest Gump", "Film description",
                LocalDate.of(1994, 6, 23), 136, new Mpa(3, "PG-13"));
        filmDao.createFilm(film1);
        Film film2 = new Film(2, "Inception", "Film description",
                LocalDate.of(2016, 10, 8), 121, new Mpa(3, "PG-13"));
        filmDao.createFilm(film2);

        assertEquals("Forrest Gump", filmDao.getFilmById(1).getName());
        assertEquals("Inception", filmDao.getFilmById(2).getName());
    }

    @Test
    void findAllFilmsFromDatabase() {

        Film film1 = new Film(1, "Forrest Gump", "Film description",
                LocalDate.of(1994, 6, 23), 136, new Mpa(3, "PG-13"));
        filmDao.createFilm(film1);

        Film film2 = new Film(2, "Inception", "Film description",
                LocalDate.of(2016, 10, 8), 121, new Mpa(3, "PG-13"));
        filmDao.createFilm(film2);

        List<Film> films = filmDao.findAllFilms();

        assertEquals(2, films.size());
    }

    @Test
    void addLikeToAddedFilm() {

        Film film1 = new Film(1, "Forrest Gump", "Film description",
                LocalDate.of(1994, 6, 23), 136, new Mpa(3, "PG-13"));
        filmDao.createFilm(film1);

        User testUser = new User(1, "daniil@mail.ru", "Daniil88", "Daniil",
                LocalDate.of(1988, 8, 14));
        userDao.createUser(testUser);

        User testUser1 = new User(1, "elena@mail.ru", "Elena222", "Elena",
                LocalDate.of(1976, 11, 2));
        userDao.createUser(testUser1);

        filmDao.addLike(film1.getId(), testUser.getId());
        filmDao.addLike(film1.getId(), testUser1.getId());

        List<Integer> filmLikes = filmDao.getFilmLikesList(film1.getId());

        assertEquals(filmLikes.size(), 2);
    }

    @Test
    void removeLikeFromAddedFilm() {

        Film film1 = new Film(1, "Forrest Gump", "Film description",
                LocalDate.of(1994, 6, 23), 136, new Mpa(3, "PG-13"));
        filmDao.createFilm(film1);
        User testUser = new User(1, "daniil@mail.ru", "Daniil88", "Daniil",
                LocalDate.of(1988, 8, 14));
        userDao.createUser(testUser);
        User testUser1 = new User(1, "elena@mail.ru", "Elena222", "Elena",
                LocalDate.of(1976, 11, 2));
        userDao.createUser(testUser1);

        filmDao.addLike(film1.getId(), testUser.getId());
        filmDao.addLike(film1.getId(), testUser1.getId());
        filmDao.removeLike(film1.getId(), testUser.getId());

        List<Integer> filmLikesList = filmDao.getFilmLikesList(film1.getId());

        assertEquals(filmLikesList.size(), 1);
    }

    @Test
    public void findMostPopularFilms() {
        Film film = new Film(1, "Forrest Gump", "Film description",
                LocalDate.of(1994, 6, 23), 136, new Mpa(3, "PG-13"));
        filmDao.createFilm(film);

        Film film2 = new Film(2, "John Wick", "Film description",
                LocalDate.of(2012, 9, 8), 172, new Mpa(4, "R"));
        filmDao.createFilm(film2);

        Film film3 = new Film(3, "Inception", "Film description",
                LocalDate.of(2016, 10, 8), 121, new Mpa(3, "PG-13"));
        filmDao.createFilm(film3);

        User user = new User(1, "daniil@mail.ru", "Daniil88", "Daniil",
                LocalDate.of(1988, 8, 14));
        userDao.createUser(user);


        filmDao.addLike(film.getId(), user.getId());
        filmDao.addLike(film2.getId(), user.getId());

        List<Film> popularFilm = filmDao.findMostPopularFilms(2);

        assertEquals(popularFilm.size(), 2);
        assertEquals("John Wick", popularFilm.get(1).getName());

    }
}
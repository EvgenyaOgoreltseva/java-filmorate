package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dao.film.FilmDao;
import ru.yandex.practicum.dao.genre.GenreDao;
import ru.yandex.practicum.dao.user.UserDao;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmDao filmDao;
    private final UserDao userDao;
    private final GenreDao genreDao;

    public List<Film> findAllFilms() {
        List<Film> list = filmDao.findAllFilms();
        log.info("Получен список всех фильмов.");
        return list;
    }

    public Film createFilm(Film film) {
        checksFilms(film);
        Film createdFilm = filmDao.createFilm(film);
        if (film.getGenres() != null) {
            genreDao.filmGenreUpdate(film.getGenres(), createdFilm.getId());
        }
        createdFilm.setGenres(genreDao.getGenresByFilmId(createdFilm.getId()));

        log.info("Фильм сохранен.");
        return createdFilm;
    }

    public Film updateFilm(Film film) {
        checksFilms(film);
        Film updatedFilm = filmDao.updateFilm(film);
        genreDao.deleteGenresByFilmId(film.getId());
        if (film.getGenres() != null) {
            genreDao.filmGenreUpdate(film.getGenres(), film.getId());
        }
        updatedFilm.setGenres(genreDao.getGenresByFilmId(film.getId()));
        updatedFilm.setLikedByUserIds(filmDao.getFilmLikesList(film.getId()));
        log.info("Данные фильма обновлены.");
        return updatedFilm;
    }

    public Film getFilmById(int id) {
        Film film = filmDao.getFilmById(id);
        film.setGenres(genreDao.getGenresByFilmId(film.getId()));
        film.setLikedByUserIds(filmDao.getFilmLikesList(film.getId()));
        log.info("Получен фильм с идентификатором " + id + ".");
        return film;
    }

    public int deleteFilm(int id) {
        log.info("Фильм удален.");
        return filmDao.deleteFilm(id);
    }

    public Film addLike(int filmId, int userId) {
        Film film = filmDao.getFilmById(filmId);
        User user = userDao.getUserById(userId);
        filmDao.addLike(filmId, userId);
        log.info("Пользователь " + user.getName() + " поставил лайк фильму " + film.getName() + ".");
        return film;
    }

    public Film removeLike(int filmId, int userId) {
        Film film = filmDao.getFilmById(filmId);
        User user = userDao.getUserById(userId);
        filmDao.removeLike(filmId, userId);
        log.info("Пользователь " + user.getName() + " убрал лайк у фильма " + film.getName() + ".");
        return film;

    }

    public List<Film> findMostPopularFilms(Integer countFilms) {
        if (countFilms < 1) {
            throw new ValidationException("Количество фильмов не может быть меньше 1!");
        }
        List<Film> allFilms = filmDao.findMostPopularFilms(countFilms);
        log.info("Список десяти самых популярных фильмов");
        return allFilms.stream().limit(countFilms).collect(Collectors.toList());
    }

    private void checksFilms(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if (film.getDescription().length() > 200 || film.getDescription() == null) {
            throw new ValidationException("Максимальная длина описания — 200 символов.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)) || film.getReleaseDate() == null) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года.");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной.");

        }
    }

}

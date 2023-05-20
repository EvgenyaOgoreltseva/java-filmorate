package ru.yandex.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exceptions.NotFoundException;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film createFilm(Film film) {
        if (film == null) {
            throw new NotFoundException("Фильм не найден.");
        }
        checksFilms(film);
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        if (film == null) {
            throw new NotFoundException("Фильм не найден.");
        }
        checksFilms(film);
        return filmStorage.updateFilm(film);
    }

    public Film getFilmById(int id) {
        if (id < 0) {
            throw new NotFoundException("Фильм с id " + id +  " не найден.");
        }
        return filmStorage.getFilmById(id);
    }

    public Film addLike(int filmId, int userId) {
        if (filmId < 0 || userId < 0) {
            throw new NotFoundException("Пользователь с id " + userId + " или фильм с id " + filmId + " не найдены.");
        }
        Film film = getFilmById(filmId);
        film.getLikedByUserIds().add(userId);
        updateFilm(film);
        log.info("Пользователь с id " + userId + " поставил лайк фильму с id " + filmId);
        return film;
    }

    public Film removeLike(int filmId, int userId) {
        if (filmId < 0 || userId < 0) {
            throw new NotFoundException("Пользователь с id " + userId + " или фильм с id " + filmId + " не найдены.");
        }
        Film film = getFilmById(filmId);
        film.getLikedByUserIds().remove(userId);
        updateFilm(film);
        log.info("Пользователь с id " + userId + " удалил лайк фильму с id " + filmId);
        return film;

    }

    public List<Film> findMostPopularFilms(Integer countFilms) {
        if (countFilms < 1) {
            throw new ValidationException("Фильмы не найдены");
        }
        List<Film> allFilms = filmStorage.findAllFilms();
        allFilms.sort((f1, f2) -> Integer.compare(f2.getLikedByUserIds().size(), f1.getLikedByUserIds().size()));
        log.info("Получен список самых популярных фильмов");
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

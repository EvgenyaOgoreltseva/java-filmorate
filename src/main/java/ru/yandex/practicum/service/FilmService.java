package ru.yandex.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exceptions.NotFoundException;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.storage.FilmStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
        checksFilms(film);
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        checksFilms(film);
        return filmStorage.updateFilm(film);
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public Film addLike(int filmId, int userId) {
        if (filmId < 0 || userId < 0) {
            throw new NotFoundException("Данный" + userId + "," + filmId + "не найден");
        }
        Film film = getFilmById(filmId);
        film.getLikedByUserIds().add(userId);
        updateFilm(film);
        return film;
    }

    public Film removeLike(int filmId, int userId) {
        if (filmId < 0 || userId < 0) {
            throw new NotFoundException("Данный" + userId + "," + filmId + "не найден");
        }
        Film film = getFilmById(filmId);
        film.getLikedByUserIds().remove(userId);
        updateFilm(film);
        return film;

    }

    public List<Film> findMostPopularFilms(Integer countFilms) {
        List<Film> allFilms = filmStorage.findAllFilms();
        allFilms.sort((f1, f2) -> Integer.compare(f2.getLikedByUserIds().size(), f1.getLikedByUserIds().size()));
        return allFilms.stream().limit(countFilms).collect(Collectors.toList());
    }

    private void checksFilms(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года.");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        }

    }
}

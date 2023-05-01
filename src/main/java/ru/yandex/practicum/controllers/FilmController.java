package ru.yandex.practicum.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private int id = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    private int generateId() {
        return id++;
    }

    @GetMapping
    public ArrayList<Film> findAll() {
        log.info("Список фильмов получен.");
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        checksFilms(film);
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Фильм сохранен.");
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        checksFilms(film);
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Фильма с id " + film.getId() + " не найдено");
        }
        films.put(film.getId(), film);
        log.info("Фильм с id " + film.getId() + " обновлен.");
        return film;
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

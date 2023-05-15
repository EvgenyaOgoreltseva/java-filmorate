package ru.yandex.practicum.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceptions.NotFoundException;
import ru.yandex.practicum.exceptions.ValidationException;
import ru.yandex.practicum.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final Map<Integer,Film> films = new HashMap<>();
    private int id = 1 ;

    private int generateId () {
        return id++;
    }

    @Override
    public List<Film> findAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Фильм сохранен.");
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильма с id " + film.getId() + " не найдено");
        }
        films.put(film.getId(), film);
        log.info("Фильм с id " + film.getId() + " обновлен.");
        return film;
    }

    @Override
    public Film getFilmById(int id) {
        if (!films.containsKey(id)){
            throw new NotFoundException("Фильма с id " + id +  "не найдено");
        }
        log.info("Фильм с id " + id + " найден.");
        return films.get(id);
    }
}

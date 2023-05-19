package ru.yandex.practicum.storage.film;

import ru.yandex.practicum.model.Film;

import java.util.List;


public interface FilmStorage {
    List<Film> findAllFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(int id);
}

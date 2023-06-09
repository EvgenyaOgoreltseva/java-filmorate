package ru.yandex.practicum.dao.film;

import ru.yandex.practicum.model.Film;

import java.util.List;

public interface FilmDao {

    List<Film> findAllFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    int deleteFilm(int id);

    Film getFilmById(int id);

    List<Film> findMostPopularFilms(int count);

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);

    List<Integer> getFilmLikesList(int filmId);
}

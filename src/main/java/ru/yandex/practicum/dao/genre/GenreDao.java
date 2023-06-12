package ru.yandex.practicum.dao.genre;

import ru.yandex.practicum.model.Genre;

import java.util.List;

public interface GenreDao {
    Genre getById(int id);

    List<Genre> findAllGenres();

    List<Genre> getGenresByFilmId(int filmId);

    void filmGenreUpdate(List<Genre> genreList, Integer filmId);

    void deleteGenresByFilmId(int filmId);
}

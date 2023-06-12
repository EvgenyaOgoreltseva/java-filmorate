package ru.yandex.practicum.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmService filmService;


    @GetMapping
    public List<Film> findAllFilms() {
        log.info("Получен запрос на добавление фильма");
        return filmService.findAllFilms();
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен запрос на обновление фильма с ID - " + film.getId());
        return filmService.updateFilm(film);
    }

    @DeleteMapping
    public int deleteFirm(@RequestBody int filmId) {
        log.info("Получен запрос на удаление фильма с ID - " + filmId);
        return filmService.deleteFilm(filmId);
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable int filmId) {
        log.info("Получен запрос на получение фильма с ID - " + filmId);
        return filmService.getFilmById(filmId);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable("filmId") int filmId, @PathVariable("userId") int userId) {
        log.info("Получен запрос на добавление лайка к фильму с ID - " + filmId + " от пользователя с ID - " + userId);
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film removeLike(@PathVariable("filmId") int filmId, @PathVariable("userId") int userId) {
        log.info("Получен запрос на удаление лайка к фильму с ID - " + filmId + " от пользователя с ID - " + userId);
        return filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> findMostPopularFilms(@RequestParam(name = "count", defaultValue = "10", required = false) Integer countFilms) {
        log.info("Получен запрос на получение 10 самых популярных фильмов");
        return filmService.findMostPopularFilms(countFilms);
    }
}

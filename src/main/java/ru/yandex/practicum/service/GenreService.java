package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dao.genre.GenreDao;
import ru.yandex.practicum.exceptions.NotFoundException;
import ru.yandex.practicum.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {
    private final GenreDao genreDao;


    public Genre getById(int id) {
        if (id < 0) {
            throw new NotFoundException("Неверно передан id жанра.");
        }
        log.info("Получен жанр с id " + id + ".");
        return genreDao.getById(id);
    }

    public List<Genre> findAllGenres() {
        log.info("Получен список всех жанров.");
        return genreDao.findAllGenres();
    }
}

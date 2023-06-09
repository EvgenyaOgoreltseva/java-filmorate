package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dao.mparating.MpaRatingDao;
import ru.yandex.practicum.exceptions.NotFoundException;
import ru.yandex.practicum.model.Mpa;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpaService {

    private final MpaRatingDao mpaRatingDao;

    public Mpa getById(int id) {
        if (id < 0) {
            throw new NotFoundException("Неверный ID рейтинга MPA");
        }
        log.info("Получен рейтинг MPA с ID" + id);
        return mpaRatingDao.getById(id);
    }

    public List<Mpa> findAllRatings() {
        log.info("Получен список всех рейтингов MPA");
        return mpaRatingDao.getAllRatings();
    }
}

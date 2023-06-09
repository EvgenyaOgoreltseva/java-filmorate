package ru.yandex.practicum.dao.mparating;

import ru.yandex.practicum.model.Mpa;

import java.util.List;

public interface MpaRatingDao {

    Mpa getById(int id);

    List<Mpa> getAllRatings();
}

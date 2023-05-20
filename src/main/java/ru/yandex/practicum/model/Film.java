package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;


@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Ошибка валидации! Имя не может быть пустым!")
    private String name;
    @Size(min = 1, max = 200, message = "Ошибка валидации! Описание должно содержать от 1 до 200 символов!")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Ошибка валидации! Продолжительность должна быть больше нуля!")
    private int duration;
    private final HashSet<Integer> likedByUserIds = new HashSet<>();


}


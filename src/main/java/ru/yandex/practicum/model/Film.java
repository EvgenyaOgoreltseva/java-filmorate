package ru.yandex.practicum.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {
    private int id;
    @NotBlank(message = "Ошибка валидации! Имя не может быть пустым!")
    private String name;
    @Size(min = 1, max = 200, message = "Ошибка валидации! Описание должно содержать от 1 до 200 символов!")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Ошибка валидации! Продолжительность должна быть больше нуля!")
    private int duration;
    private Mpa mpa;
    private List<Genre> genres;
    private List<Integer> likedByUserIds;


    //для unit tests
    public Film(int id, String name, String description, LocalDate releaseDate, int duration, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }
}

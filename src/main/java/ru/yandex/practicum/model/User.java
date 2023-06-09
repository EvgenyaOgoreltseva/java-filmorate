package ru.yandex.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    @NonNull
    private int id;
    @NotBlank(message = "Ошибка валидации! Email не может быть пустым!")
    @Email(message = "Ошибка валидации! Email должен содержать символ '@'!")
    private String email;
    private String login;
    @NotBlank(message = "Ошибка валидации! Имя не может быть пустым!")
    private String name;
    @PastOrPresent(message = "Ошибка валидации! День рождения не может быть в будущем!")
    private LocalDate birthday;
    private final Set<Integer> friendIds = new HashSet<>();

}

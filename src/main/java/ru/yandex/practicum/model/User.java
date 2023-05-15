package ru.yandex.practicum.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class User {
    @NonNull
    private int id;
    private final String email;
    private final String login;
    @NonNull
    private String name;
    @NonNull
    private LocalDate birthday;
    private final Set<Integer> friendIds = new HashSet<>();
}

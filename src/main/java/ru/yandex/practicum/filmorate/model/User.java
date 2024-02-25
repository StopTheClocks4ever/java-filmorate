package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {

    public User() {

    }

    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Integer> friends;
}

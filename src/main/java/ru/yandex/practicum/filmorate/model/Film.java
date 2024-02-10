package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
    private long id;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final Integer duration; //продолжительность в минтутах
    private Set<Long> likes;
    private long likesSum;
}

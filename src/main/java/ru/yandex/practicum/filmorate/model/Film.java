package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class Film {

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration; //продолжительность в минтутах
    private Mpa mpa;
    private List<Genre> genres;
    private int rate;

    public Film() {
    }
}

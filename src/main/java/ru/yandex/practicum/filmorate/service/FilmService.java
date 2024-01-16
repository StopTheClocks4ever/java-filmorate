package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmService {

    private final List<Film> films = new ArrayList<>();
    private int filmId = 0;

    private int generateNewId() {
        return ++filmId;
    }

    public int getCurrentFilmId() {
        return filmId;
    }

    public void addFilm(Film film) {
        film.setId(generateNewId());
        films.add(film);
    }

    public void updateFilm(Film film) {
        films.set(film.getId() - 1, film);
    }

    public List<Film> getFilms() {
        return films;
    }
}

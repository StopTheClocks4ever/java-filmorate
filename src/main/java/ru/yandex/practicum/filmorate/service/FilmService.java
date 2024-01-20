package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmService {

    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 0;

    private int generateNewId() {
        return ++filmId;
    }

    public void addFilm(Film film) {
        film.setId(generateNewId());
        films.put(film.getId(), film);
    }

    public void updateFilm(Film film) {
        films.put(film.getId(), film);
    }

    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    public boolean isFilmExists(Film film) {
        return films.containsKey(film.getId());
    }
}

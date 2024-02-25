package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Map;

public class FilmDbStorage implements FilmStorage {

    @Override
    public void addFilm(Film film) {

    }

    @Override
    public void updateFilm(Film film) {

    }

    @Override
    public void deleteFilm(int filmId) {

    }

    @Override
    public boolean isFilmExists(Film film) {
        return false;
    }

    @Override
    public Map<Long, Film> getFilms() {
        return null;
    }
}

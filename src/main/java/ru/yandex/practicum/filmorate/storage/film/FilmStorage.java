package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

public interface FilmStorage {

    void addFilm(Film film);

    void updateFilm(Film film);

    void deleteFilm(int filmId);

    public boolean isFilmExists(Film film);

    Map<Long, Film> getFilms();
}

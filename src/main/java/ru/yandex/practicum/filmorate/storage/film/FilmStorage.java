package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Map;

public interface FilmStorage {

    void addFilm(Film film);

    void updateFilm(Film film);

    void deleteFilm(int filmId);

    boolean isFilmExists(Film film);

    List<Film> getFilms();

    Film getFilmById(int id);

    List<Mpa> getAllMpa();

    Mpa getMpaById(int id);

    List<Genre> getAllGenres();

    Genre getGenreById(int id);

    void addLike(int id, int userId);

    void deleteLike(int id, int userId);
}

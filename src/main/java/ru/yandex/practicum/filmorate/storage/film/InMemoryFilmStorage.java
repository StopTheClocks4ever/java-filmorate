package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final List<Film> films = new ArrayList<>();
    private int filmId = 0;

    private int generateNewId() {
        return ++filmId;
    }

    public void addFilm(Film film) {

    }

    public void updateFilm(Film film) {

    }

    public void deleteFilm(int filmId) {
        films.remove(filmId);
    }

    public List<Film> getFilms() {
        return films;
    }

    @Override
    public Film getFilmById(int id) {
        return null;
    }

    @Override
    public List<Mpa> getAllMpa() {
        return null;
    }

    @Override
    public Mpa getMpaById(int id) {
        return null;
    }

    @Override
    public List<Genre> getAllGenres() {
        return null;
    }

    @Override
    public Genre getGenreById(int id) {
        return null;
    }

    @Override
    public void addLike(int id, int userId) {

    }

    @Override
    public void deleteLike(int id, int userId) {

    }

    public boolean isFilmExists(Film film) {
        return films.contains(film);
    }
}

package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private int filmId = 0;

    private int generateNewId() {
        return ++filmId;
    }

    public void addFilm(Film film) {
        film.setId(generateNewId());
        film.setLikes(new HashSet<>());
        films.put(film.getId(), film);
    }

    public void updateFilm(Film film) {
        film.setLikes(new HashSet<>());
        films.put(film.getId(), film);
    }

    public void deleteFilm(int filmId) {
        films.remove(filmId);
    }

    public Map<Long, Film> getFilms() {
        return films;
    }

    public boolean isFilmExists(Film film) {
        return films.containsKey(film.getId());
    }
}

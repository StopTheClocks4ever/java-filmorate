package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {

    @Autowired
    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;
    private final FilmService service;

    public FilmController(@Qualifier("filmDbStorage") FilmStorage filmStorage, FilmService service) {
        this.filmStorage = filmStorage;
        this.service = service;
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) {
        log.info("Получен запрос POST /film");
        FilmValidator.validate(film);
        filmStorage.addFilm(film);
        log.info("Запрос успешно обработан");
        int rate = film.getRate();
        Film newFilm = getFilmById(film.getId());
        newFilm.setRate(rate);
        return newFilm;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен запрос PUT /films");
        List<Film> films = getFilms();
        Map<Integer, Film> filmsAndId = new HashMap<>();
        for (Film checkFilm: films) {
            filmsAndId.put(checkFilm.getId(), checkFilm);
        }
        if (filmsAndId.containsKey(film.getId())) {
            FilmValidator.validate(film);
            filmStorage.updateFilm(film);
            log.info("Запрос успешно обработан");
            return getFilmById(film.getId());
        } else {
            throw new FilmNotFoundException("Такого фильма не существует.");
        }
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен запрос GET /films");
        return new ArrayList<>(filmStorage.getFilms());
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Получен запрос GET /films/id");
        return filmStorage.getFilmById(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос PUT /films/{id}/like/{userId}");
        service.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        service.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getTop(@RequestParam (value = "count", defaultValue = "10", required = false) Integer count) {
        return service.getTop(count);
    }

    @GetMapping("/mpa")
    public List<Mpa> getAllMpa() {
        return filmStorage.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        return filmStorage.getMpaById(id);
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return filmStorage.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable int id) {
        return filmStorage.getGenreById(id);
    }
}

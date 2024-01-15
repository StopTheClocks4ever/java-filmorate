package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class FilmController {

    private final List<Film> films = new ArrayList<>();
    private int filmId = 0;

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) {
        log.info("Получен запрос POST /film");
        if (FilmValidator.validate(film)) {
            film.setId(generateNewId());
            films.add(film);
            log.info("Запрос успешно обработан");
            return film;
        }
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен запрос PUT /films");
        if (filmId < film.getId()) {
            log.warn("Такого фильма не существует.");
            throw new ValidationException("Такого фильма не существует.");
        }
        if (FilmValidator.validate(film)) {
            films.set(film.getId()-1, film);
            log.info("Запрос успешно обработан");
            return film;
        }
        return film;
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен запрос GET /films");
        return films;
    }

    private int generateNewId() {
        return ++filmId;
    }
}

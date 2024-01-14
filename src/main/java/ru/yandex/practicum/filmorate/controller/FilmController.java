package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 0;

    @PostMapping("/film")
    public void addFilm(@RequestBody Film film) {
        log.info("Получен запрос POST /film");
        try {
            if (FilmValidator.validate(film)) {
                film.setId(generateNewId());
                films.put(film.getId(), film);
                log.info("Запрос успешно обработан");
            }
        } catch (ValidationException e) {
            log.warn("Фильм не прошел валидацию");
        }
    }

    @PutMapping("/film")
    public void updateFilm(@RequestBody Film film) {
        log.info("Получен запрос PUT /film");
        try {
            if (FilmValidator.validate(film)) {
                films.put(film.getId(), film);
                log.info("Запрос успешно обработан");
            }
        } catch (ValidationException e) {
            log.warn("Фильм не прошел валидацию");
        }
    }

    @GetMapping("/films")
    public Map<Integer, Film> getFilms() {
        log.info("Получен запрос GET /films");
        return films;
    }

    private int generateNewId() {
        return ++filmId;
    }
}

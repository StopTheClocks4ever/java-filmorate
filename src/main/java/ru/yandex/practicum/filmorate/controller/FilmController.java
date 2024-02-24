package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmStorage inMemoryFilmStorage;
    private final FilmService service;

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) {
        log.info("Получен запрос POST /film");
        if (FilmValidator.validate(film)) {
            inMemoryFilmStorage.addFilm(film);
            log.info("Запрос успешно обработан");
            return film;
        }
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) {
        log.info("Получен запрос PUT /films");
        if (!inMemoryFilmStorage.isFilmExists(film)) {
            log.warn("Такого фильма не существует.");
            throw new FilmNotFoundException("Такого фильма не существует.");
        }
        if (FilmValidator.validate(film)) {
            inMemoryFilmStorage.updateFilm(film);
            log.info("Запрос успешно обработан");
            return film;
        }
        return film;
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен запрос GET /films");
        return new ArrayList<>(inMemoryFilmStorage.getFilms().values());
    }

    @GetMapping("/films/{id}")
    public Film getUserById(@PathVariable long id) {
        if (!inMemoryFilmStorage.getFilms().containsKey(id)) {
            log.warn("Такого фильма не существует.");
            throw new FilmNotFoundException("Такого фильма не существует.");
        }
        log.info("Получен запрос GET /films/id");
        return inMemoryFilmStorage.getFilms().get(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void likeFilm(@PathVariable long id, @PathVariable long userId) {
        log.info("Получен запрос PUT /films/{id}/like/{userId}");
        service.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        service.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getUserFriends(@RequestParam (value = "count", defaultValue = "10", required = false) Integer count) {
        return service.getTop(count);
    }
}

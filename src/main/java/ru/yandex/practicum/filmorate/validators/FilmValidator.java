package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class FilmValidator {

    public static boolean validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Название фильма не может быть пустым.");
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if (film.getDescription().length() > 200) {
            log.warn("Длина описания больше 200 символов.");
            throw new ValidationException("Длина описания больше 200 символов.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            log.warn("Неверная дата релиза.");
            throw new ValidationException("Неверная дата релиза.");
        }
        if (film.getDuration() <= 0) {
            log.warn("Неверная продолжительность фильма.");
            throw new ValidationException("Неверная продолжительность фильма.");
        }
        return true;
    }
}

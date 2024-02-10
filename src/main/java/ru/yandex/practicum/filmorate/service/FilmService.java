package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage inMemoryFilmStorage;

    public void addLike(long filmId, long userId) {
        Film film = inMemoryFilmStorage.getFilms().get(filmId);
        if (!(film.getLikes().contains(userId))) {
            film.getLikes().add(userId);
            film.setLikesSum(film.getLikesSum() + 1);
        }
    }

    public void deleteLike(long filmId, long userId) {
        if (!inMemoryFilmStorage.getFilms().containsKey(filmId)) {
            throw new FilmNotFoundException("Указанного фильма не существует");
        }
        Film film = inMemoryFilmStorage.getFilms().get(filmId);
        if (!(film.getLikes().contains(userId))) {
            throw new UserNotFoundException("Указанного пользователя не существует");
        }
            film.getLikes().remove(userId);
            film.setLikesSum(film.getLikesSum() + 1);
    }

    public List<Film> getTop(int count) {
        List<Film> films = new ArrayList<>(inMemoryFilmStorage.getFilms().values());
        return films.stream()
                .sorted(Comparator.comparing(Film::getLikesSum).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}

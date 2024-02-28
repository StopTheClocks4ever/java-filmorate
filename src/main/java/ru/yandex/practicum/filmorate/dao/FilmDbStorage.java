package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.LikeNotFoundException;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFilm(Film film) {
        String sqlQuery = "INSERT INTO Films (name, description, release_date, duration, rating_id, rate) VALUES(?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            stmt.setInt(6, film.getRate());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
        if (film.getGenres() != null) {
            String sqlQuery2 = "INSERT INTO Film_genre (film_id, genre_id) VALUES(?, ?)";

            List<Genre> genres = film.getGenres();
            for (Genre element:genres) {
                jdbcTemplate.update(sqlQuery2, film.getId(), element.getId());
            }
        }
    }

    @Override
    public void updateFilm(Film film) {
        jdbcTemplate.update("UPDATE Films SET name=?, description=?, release_date=?, duration=?, rating_id=?, rate=? WHERE id=?",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getRate(), film.getId());

        if (film.getGenres() != null) {
            jdbcTemplate.update("DELETE FROM Film_genre WHERE film_id=?", film.getId());
            String sqlQuery2 = "INSERT INTO Film_genre (film_id, genre_id) VALUES(?, ?)";
            List<Genre> genres = film.getGenres().stream().distinct().collect(Collectors.toList());
            for (Genre element:genres) {
                jdbcTemplate.update(sqlQuery2, film.getId(), element.getId());
            }
        }
    }

    @Override
    public void deleteFilm(int filmId) {

    }

    @Override
    public boolean isFilmExists(Film film) {
        return false;
    }

    @Override
    public List<Film> getFilms() {
        List<Film> films = jdbcTemplate.query("SELECT * " +
                                      "FROM Films AS f " +
                                      "INNER JOIN rating AS r ON f.rating_id=r.id", new FilmMapper());

        for (Film film:films) {
            List<Genre> filmGenre = jdbcTemplate.query("SELECT genre_id, genre_name " +
                                                           "FROM film_genre AS fg " +
                                                           "INNER JOIN genre AS g ON fg.genre_id=g.id " +
                                                           "WHERE film_id=? ORDER BY genre_id", new GenreMapper(), film.getId());
            Set<Genre> s = new HashSet<>(filmGenre);
            film.setGenres(filmGenre);
        }
        return films;
    }

    public Film getFilmById(int id) {
        List<Film> films = getFilms();
        for (Film checkFilm : films) {
            int checkId = checkFilm.getId();
            if (checkId == id) {
                return checkFilm;
            }
        }
        throw new FilmNotFoundException("Такого фильма не существует.");
    }

    public List<Mpa> getAllMpa() {
        return jdbcTemplate.query("SELECT * FROM rating", new MpaMapper());
    }

    public Mpa getMpaById(int id) {
        List<Mpa> mpaList = jdbcTemplate.query("SELECT * FROM rating WHERE id=?", new MpaMapper(), id);
        if (!mpaList.isEmpty()) {
            Mpa mpa = mpaList.get(0);
            return mpa;
        } else {
            throw new MpaNotFoundException("Такого mpa не существует");
        }
    }

    public List<Genre> getAllGenres() {
        return jdbcTemplate.query("SELECT id AS genre_id, genre_name FROM genre", new GenreMapper());
    }

    public Genre getGenreById(int id) {
        List<Genre> genreList = jdbcTemplate.query("SELECT id AS genre_id, genre_name FROM genre WHERE id=?", new GenreMapper(), id);
        if (!genreList.isEmpty()) {
            Genre genre = genreList.get(0);
            return genre;
        } else {
            throw new GenreNotFoundException("Такого жанра не существует");
        }
    }

    public void addLike(int id, int userId) {
        List<Like> likesList = jdbcTemplate.query("SELECT film_id, user_id FROM likes WHERE film_id=? AND user_id=?", new LikeMapper(), id, userId);
        Film film = getFilmById(id);
        if (likesList.isEmpty()) {
            jdbcTemplate.update("INSERT INTO Likes (film_id, user_id) VALUES(?, ?)", id, userId);
            film.setRate(film.getRate() + 1);
            updateFilm(film);
        }
    }

    public void deleteLike(int id, int userId) {
        List<Like> likesList = jdbcTemplate.query("SELECT film_id, user_id FROM likes WHERE film_id=? AND user_id=?", new LikeMapper(), id, userId);
        Film film = getFilmById(id);
        if (!likesList.isEmpty()) {
            jdbcTemplate.update("DELETE FROM Likes WHERE film_id=? AND user_id=?", id, userId);
            film.setRate(film.getRate() - 1);
        } else {
            throw new LikeNotFoundException("Лайка у указаного фильма от этого пользователя нет");
        }
    }
}

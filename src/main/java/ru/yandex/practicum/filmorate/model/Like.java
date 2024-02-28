package ru.yandex.practicum.filmorate.model;

public class Like {

    private int filmId;
    private int userId;

    public Like() {

    }

    public Like(int filmId, int userId) {
        this.filmId = filmId;
        this.userId = userId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmorateApplicationTests {
	Set<Long> friendSet = new HashSet<>();
	Set<Long> likesSet = new HashSet<>();
	User userBlankEmail = new User(1,"", "login", "name", LocalDate.parse("1990-01-01"), friendSet);
	User userWrongEmail = new User(1,"email", "login", "name", LocalDate.parse("1990-01-01"), friendSet);
	User userBlankLogin = new User(1,"email@gmail.com", "", "name", LocalDate.parse("1990-01-01"), friendSet);
	User userSpaceLogin = new User(1,"email@gmail.com", "login login", "name", LocalDate.parse("1990-01-01"), friendSet);
	User userBlankName = new User(1,"email@gmail.com", "login", "", LocalDate.parse("1990-01-01"), friendSet);
	User userFutureDate = new User(1,"email@gmail.com", "login", "name", LocalDate.parse("2100-01-01"), friendSet);

	Film filmBlankName = new Film(1, "", "description", LocalDate.parse("1990-01-01"), 90, likesSet, 0);
	Film filmMaxLength = new Film(1, "name", "Lorem ipsum dolor sit amet, consectetuer adipiscing " +
			"elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi " +
			"enim ad minim veniam, quis nostrud exerci tation ull", LocalDate.parse("1990-01-01"), 90, likesSet, 0);
	Film filmReleaseDate = new Film(1, "name", "description", LocalDate.parse("1800-01-01"), 90, likesSet, 0);
	Film filmDuration = new Film(1, "name", "description", LocalDate.parse("1990-01-01"), -90, likesSet, 0);

	@Test
	void userValidationTest() {
		assertThrows(ValidationException.class, () -> UserValidator.validate(userBlankEmail));
		assertThrows(ValidationException.class, () -> UserValidator.validate(userWrongEmail));
		assertThrows(ValidationException.class, () -> UserValidator.validate(userBlankLogin));
		assertThrows(ValidationException.class, () -> UserValidator.validate(userSpaceLogin));
		UserValidator.validate(userBlankName);
		assertEquals("login", userBlankName.getName());
		assertThrows(ValidationException.class, () -> UserValidator.validate(userFutureDate));
	}

	@Test
	void filmValidationTest() {
		assertThrows(ValidationException.class, () -> FilmValidator.validate(filmBlankName));
		assertThrows(ValidationException.class, () -> FilmValidator.validate(filmMaxLength));
		assertThrows(ValidationException.class, () -> FilmValidator.validate(filmReleaseDate));
		assertThrows(ValidationException.class, () -> FilmValidator.validate(filmDuration));
	}

}

package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.storage.user.UserDaoStorage;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeDaoStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserDaoStorage userDaoStorage;

    @Override
    public void putLike(int id, int userId) {
        String sql =
                "merge into FILMS_LIKES(FILM_ID, USER_ID) " +
                        "VALUES (?, ?)";

        if (jdbcTemplate.update(sql, id, userId) == 0) {
            throw new EntityNotFoundException(
                    String.format("Ошибка при добавлении в БД LIKES, filmID=%s, userID=%s.", id, userId));
        }
    }

    @Override
    public void removeLikes(int id, int userId) {
        if (userDaoStorage.getUsers().stream().noneMatch(u -> Objects.equals(u.getId(), userId))) {
            throw new UserNotFoundException("Идентификатор пользователя не найден");
        }
        if (id < 1) {
            throw new EntityNotFoundException("Лайк не найден");
        }
        String sql =
                "DELETE " +
                        "FROM FILMS_LIKES " +
                        "WHERE FILM_ID = ? " +
                        "AND USER_ID = ?";
        jdbcTemplate.update(sql, id, userId);
    }
}

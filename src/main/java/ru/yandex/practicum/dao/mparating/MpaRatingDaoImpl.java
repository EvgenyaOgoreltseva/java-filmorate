package ru.yandex.practicum.dao.mparating;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exceptions.NotFoundException;
import ru.yandex.practicum.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaRatingDaoImpl implements MpaRatingDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa getById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM MPA_rating WHERE MPA_id = ?", this::buildMpa, id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("Рейтинг с id " + id + " не найден.");
        }

    }

    @Override
    public List<Mpa> getAllRatings() {
        return jdbcTemplate.query("SELECT * FROM MPA_rating", this::buildMpa);
    }

    private Mpa buildMpa(ResultSet rs, int rowNum) throws SQLException {
        int mpaId = rs.getInt("MPA_id");
        String mpaName = rs.getString("MPA_name");
        return new Mpa(mpaId, mpaName);
    }
}

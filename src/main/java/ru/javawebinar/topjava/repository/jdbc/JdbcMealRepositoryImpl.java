package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final Comparator<Meal> MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime).reversed();

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource) {
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Objects.requireNonNull(meal);


        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("user_id", userId)
                .addValue("dateTime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());

        if (meal.isNew()) {

            Number newKey = insertMeal.executeAndReturnKey(map);

            meal.setId(newKey.intValue());

        } else if (update(meal, userId, meal.getId()) == 0) {
            return null;
        }

        return meal;
    }

    public int update(Meal meal, int userId, int mealId) {

        return jdbcTemplate.update("UPDATE meals SET dateTime=?, description=?, calories=?  WHERE user_id=? AND id=?", meal.getDateTime(), meal.getDescription(), meal.getCalories(), userId, mealId);
    }

    @Override
    public boolean delete(int id, int userId) {

        return jdbcTemplate.update("DELETE FROM meals WHERE user_id=? AND id=?", userId, id) != 0;

    }

    @Override
    public Meal get(int id, int userId) {

        return jdbcTemplate.queryForObject("SELECT * FROM meals WHERE user_id=? AND id=?", ROW_MAPPER, userId, id);
    }

    @Override
    public List<Meal> getAll(int userId) {

        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals  WHERE user_id=? ORDER BY id", ROW_MAPPER, userId);
        meals.sort(MEAL_COMPARATOR);

        return meals;

    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);

        List<Meal> meals = getAll(userId);

        return meals.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime(), startDate, endDate))
                .collect(Collectors.toList());

    }
}


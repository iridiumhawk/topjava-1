package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;
import static ru.javawebinar.topjava.util.TimeUtil.DATE_TIME_FORMATTER;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final List<Meal> USER_MEAL_LIST = Arrays.asList(
            new Meal(1000,LocalDateTime.parse("2016-09-20 10:00", DATE_TIME_FORMATTER) , "Завтрак", 500),
            new Meal(1001,LocalDateTime.parse("2016-09-20 19:00", DATE_TIME_FORMATTER) , "Ужин", 500),
            new Meal(1002,LocalDateTime.parse("2016-09-20 14:00", DATE_TIME_FORMATTER) , "Обед", 1000)

    );

    public static final List<Meal> ADMIN_MEAL_LIST = Arrays.asList(
            new Meal(1003,LocalDateTime.parse("2016-09-20 10:00", DATE_TIME_FORMATTER) , "Завтрак", 500),
            new Meal(1004,LocalDateTime.parse("2016-09-20 19:00", DATE_TIME_FORMATTER) , "Ужин", 500),
            new Meal(1005,LocalDateTime.parse("2016-09-20 14:00", DATE_TIME_FORMATTER) , "Обед", 1010)

    );

}

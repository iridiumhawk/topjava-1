package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class AuthorizedUser {

    private static Integer id = 0;

/*    public Integer getId() {
        return id;
    }
*/
    public static void setId(int userId) {
        id = userId;
    }

    public static int getId() {
        return id;
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}

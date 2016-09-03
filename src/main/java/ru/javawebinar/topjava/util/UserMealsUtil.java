package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//                .forEach(v -> System.out.println(v));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        /*Реализовать метод UserMealsUtil.getFilteredWithExceeded:
-  должны возвращаться только записи между startTime и endTime
-  поле UserMealWithExceed.exceed должно показывать,
                                     превышает ли сумма калорий за весь день параметра метода caloriesPerDay

Т.е UserMealWithExceed - это запись одной еды, но поле exceeded будет одинаково для всех записей за этот день.

- Проверте результат выполнения ДЗ (можно проверить логику в http://topjava.herokuapp.com , список еды)
- Оцените Time complexity вашего алгоритма, если он O(N*N)- попробуйте сделать O(N).*/

        Map<LocalDate, Integer> mealMap =
                mealList.stream()
                        .collect(Collectors.toMap(x -> x.getDateTime().toLocalDate(), x -> x.getCalories() , Integer::sum));


//        mealMap.forEach((k,v) -> System.out.println(k+":"+v));

        List<UserMealWithExceed> listExceed =
                mealList.stream()
                        .filter(t -> TimeUtil.isBetween(t.getDateTime().toLocalTime(), startTime, endTime))
                        .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), mealMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                        .collect(Collectors.toList());

//        listExceed.forEach(System.out::println);


        return listExceed;
    }
}

package ru.javawebinar.topjava.repository.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);


    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
        }

        if (meal.getUserId() == userId) {
            repository.put(meal.getId(), meal);
            return meal;
        }

        return null;
    }

    @Override
    public boolean delete(int id, int userId) {

        return repository.get(id).getUserId() == userId && repository.remove(id) != null;

    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal.getUserId() == userId) {
            return meal;
        } else return null;
    }


    @Override
    public Collection<Meal> getAll(int userId) { //фильтровать по userid
        return repository.values().stream().filter(meal -> meal.getUserId() == userId).
                sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime())).collect(Collectors.toList()); //reverse?
    }
}

/*AuthorizedUser известен только на слое web (MealService можно тестировать без подмены логики авторизации), принимаем в методах сервиса и репозитория параметр userId: id владельца еды.*/
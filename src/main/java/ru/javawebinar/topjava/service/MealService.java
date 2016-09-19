package ru.javawebinar.topjava.service;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
//    Meal save(Meal meal);

    List<Meal> getAll(int userId);

    Meal get(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    void update(Meal meal,int userId);

//    void create(Meal meal,int userId);

}

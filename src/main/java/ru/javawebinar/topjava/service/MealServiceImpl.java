package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId); //check

    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal meal = repository.get(id, userId);
        if (meal != null) {
            return meal;
        } else throw new NotFoundException("get not authorized access");

    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (!repository.delete(id, userId)) {
            throw new NotFoundException("delete not authorized access");
        }

    }

    @Override
    public void update(Meal meal, int userId) {
        if (repository.save(meal, userId) == null){
            throw new NotFoundException("update not authorized access");
        }

    }

/*    @Override
    public void create(Meal meal, int userId) {
        if (AuthorizedUser.id() == 1) {
            repository.save(meal, userId);
        } else throw new NotFoundException("not authorized access");
    }*/
}
/*AuthorizedUser известен только на слое web (MealService можно тестировать без подмены логики авторизации), принимаем в методах сервиса и репозитория параметр userId: id владельца еды.*/
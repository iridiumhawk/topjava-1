package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
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
    public List<Meal> getAll() {
        return (List) repository.getAll(); //check

    }

    @Override
    public Meal get(int id) throws NotFoundException {
        if (AuthorizedUser.id() == 1) {
            return repository.get(id);
        } else throw new NotFoundException("not authorized access");

    }

    @Override
    public void delete(int id) throws NotFoundException {
        if (AuthorizedUser.id() == 1) {
            repository.delete(id);
        } else throw new NotFoundException("not authorized access");
    }

    @Override
    public void update(Meal meal) {
        if (AuthorizedUser.id() == 1) {
            repository.save(meal);
        } else throw new NotFoundException("not authorized access");

    }

    @Override
    public void create(Meal meal) {
        if (AuthorizedUser.id() == 1) {
            repository.save(meal);
        } else throw new NotFoundException("not authorized access");
    }
}
/*AuthorizedUser известен только на слое web (MealService можно тестировать без подмены логики авторизации), принимаем в методах сервиса и репозитория параметр userId: id владельца еды.*/
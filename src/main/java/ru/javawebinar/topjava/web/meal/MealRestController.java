package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    public List<MealWithExceed> getAll() {
        LOG.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY); //get from profile
    }

    public Meal get(int id) {
        LOG.info("getId " + id);
        return service.get(id);
    }

//Проверьте корректную обработку пустых значений date и time в контроллере
    public List<MealWithExceed> getFilteredByTime(LocalTime startTime, LocalTime endtTime) {
        LOG.info("getFiltered ");
        return MealsUtil.getFilteredWithExceeded(service.getAll(), startTime, endtTime, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
/*

    public List<MealWithExceed> getFilteredByTime(LocalDate startTime, LocalDate endtTime) {
        LOG.info("getFiltered ");
        return MealsUtil.getFilteredWithExceeded(service.getAll(), startTime, endtTime, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
*/


    public void delete(int id) {
        LOG.info("delId " + id);
        service.delete(id);
    }

    public void create(Meal meal) {
        LOG.info("create ");
        service.create(meal);
    }


    public void update(Meal meal) {
        LOG.info("update " );
        service.update(meal);
    }


}
/*MealRestController должен уметь обрабатывать запросы:
Отдать свою еду (для отображения в таблице, формат List<MealWithExceed>), запрос БЕЗ параметров
Отдать свою еду, отфильтрованную по startDate, startTime, endDate, endTime
Отдать/удалить свою еду по id, параметр запроса - id еды. Если еда с этим id чужая или отсутствует - NotFoundException
Сохранить/обновить еду, параметр запроса - Meal. Если обновляемая еда с этим id чужая или отсутствует - NotFoundException*/
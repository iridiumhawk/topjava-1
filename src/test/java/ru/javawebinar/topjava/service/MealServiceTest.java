package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepositoryImpl;
import ru.javawebinar.topjava.util.DbPopulatorMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;

import static ru.javawebinar.topjava.MealTestData.*;


/**
 * Created by hawk on 27.09.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {
    private static final Comparator<Meal> MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime).reversed();
    @Autowired
    protected MealService service;


    @Autowired
    private DbPopulatorMeal dbPopulator;


    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(USER_MEAL_LIST.get(0).getId(), USER_ID);
        MATCHER.assertEquals(USER_MEAL_LIST.get(0), meal);

        Meal mealAdmin = service.get(ADMIN_MEAL_LIST.get(0).getId(), ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL_LIST.get(0), mealAdmin);

    }


    @Test
    public void testDelete() throws Exception {
        service.delete(USER_MEAL_LIST.get(0).getId(), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL_LIST.get(1), USER_MEAL_LIST.get(2)), service.getAll(USER_ID));

        service.delete(ADMIN_MEAL_LIST.get(0).getId(), ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL_LIST.get(1), ADMIN_MEAL_LIST.get(2)), service.getAll(ADMIN_ID));
    }


    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, USER_ID);
        service.delete(ADMIN_MEAL_LIST.get(0).getId(), USER_ID);
        service.delete(USER_MEAL_LIST.get(0).getId(), ADMIN_ID);

    }

    //    @Test
    public void testGetBetweenDates() throws Exception {

    }

    //    @Test
    public void testGetBetweenDateTimes() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> mealList = service.getAll(ADMIN_ID);
        Collection<Meal> resultMeal = new ArrayList<>();
        ADMIN_MEAL_LIST.sort(MEAL_COMPARATOR);
        resultMeal.addAll(ADMIN_MEAL_LIST);
        MATCHER.assertCollectionEquals(resultMeal, mealList);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal newMeal = USER_MEAL_LIST.get(0);
        newMeal.setCalories(333);
        newMeal.setDescription("Food good");

        service.update(newMeal,USER_ID);

        MATCHER.assertEquals(newMeal, service.get(newMeal.getId(),USER_ID));

    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal();
        newMeal.setCalories(111);
        newMeal.setDescription("Food bad");
        newMeal.setDateTime(LocalDateTime.now());

        newMeal = service.save(newMeal,USER_ID);

        MATCHER.assertEquals(newMeal, service.get(newMeal.getId(),USER_ID));
    }
}
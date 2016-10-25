package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import java.util.Collection;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.MEALS_DELETE;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.MATCHEREXCEEDED;

/**
 * Created by hawk on 25.10.2016.
 */
public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Autowired
    MealService mealService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + USER_MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }


    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(MEALS_DELETE, mealService.getAll(USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL + "/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHEREXCEEDED.contentListMatcher(MealsUtil.getWithExceeded(MEALS, 2000)));

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testCreateMeal() throws Exception {

    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "/between?from=2015-05-30T09:00:00&to=2015-05-31T21:00:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MATCHER.contentListMatcher(MEALS))
        ;


    }
}
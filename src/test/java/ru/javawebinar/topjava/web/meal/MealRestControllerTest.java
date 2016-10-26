package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedName");
        mockMvc.perform(put(REST_URL + USER_MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk())
                .andDo(print());

        MATCHER.assertEquals(updated, mealService.get(MEAL1.getId(), USER_ID));
    }

    @Test
    public void testCreateMeal() throws Exception {
        Meal mealExpect = new Meal(null, of(2015, Month.MAY, 29, 10, 0), "Завтрак", 100);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(mealExpect)))
                .andExpect(status().isCreated())
                .andDo(print());

        Meal returned = MATCHER.fromJsonAction(action);
        mealExpect.setId(returned.getId());

        MATCHER.assertEquals(mealExpect, returned);
//        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, expected, USER), userService.getAll());
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "/between?from=2015-05-30T09:00:00&to=2015-05-31T21:00:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHEREXCEEDED.contentListMatcher(MealsUtil.getWithExceeded(MEALS, 2000)))
//                .andExpect(MATCHER.contentListMatcher(MEALS))
        ;


    }
}
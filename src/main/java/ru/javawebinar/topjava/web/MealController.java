package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


@Controller
public class MealController {
    private static final Logger LOG = LoggerFactory.getLogger(MealController.class);

    @Autowired
    private MealRestController mealRestController;

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String doPost(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            final Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                LOG.info("Create {}", meal);
                mealRestController.create(meal);
            } else {
                LOG.info("Update {}", meal);
                mealRestController.update(meal, getId(request));
            }
            return "redirect:meals";

        } else if ("filter".equals(action)) {
            LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
            LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
            LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
            LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
            model.addAttribute("meals", mealRestController.getBetween(startDate, startTime, endDate, endTime));
            return "meals";
        }

        return "meals";
    }

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }

    @RequestMapping(value = "/meals", params = "action=create", method = RequestMethod.GET)
    public String mealCreate(HttpServletRequest request, HttpServletResponse response, Model model) {

        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000);
        LOG.info("create {}", meal.getDescription());

        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/meals", params = "action=update", method = RequestMethod.GET)
    public String mealUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
        final Meal meal = mealRestController.get(getId(request));
        LOG.info("update {}", meal.getDescription());

        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/meals", params = "action=delete", method = RequestMethod.GET)
    public String mealDelete(HttpServletRequest request, HttpServletResponse response, Model model) {
        mealRestController.delete(getId(request));
        LOG.info("delete {}", getId(request));
        return "redirect:meals";
    }

//all
    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String getAll(Model model) {
        LOG.info("GetAll");
        model.addAttribute("meals", mealRestController.getAll());
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}

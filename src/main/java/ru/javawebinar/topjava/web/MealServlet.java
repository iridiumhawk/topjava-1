package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * Created by hawk on 12.09.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meals jsp 1");
//        resp.sendRedirect("mealList.jsp");

        req.setAttribute("meallist", MealsUtil.getWithExceeded(MealsUtil.getMeals(), 2000));
        req.getRequestDispatcher("/mealList.jsp").forward(req, resp);


   /*     RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);*/
    }


}

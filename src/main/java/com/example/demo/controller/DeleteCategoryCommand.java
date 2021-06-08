package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.ActivitiesDao;
import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.Constants;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public class DeleteCategoryCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(DeleteCategoryCommand.class);
    private static final long serialVersionUID = -4401946371065856871L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command starts");
        String[] categories = request.getParameterValues("categories");
        int[] array = Arrays.stream(categories).mapToInt(Integer::parseInt).toArray();
        CategoryDao.deleteCategory(array);
        LOGGER.trace("Categories removed" + Arrays.toString(categories));
        HttpSession session = request.getSession();
        session.setAttribute("activities_list", ActivitiesDao.getAllActivitiesWithCategory(Constants.ACTIVITY_NAME));
        LOGGER.info("Set the session attribute: activities_list --> " + ActivitiesDao.getAllActivitiesWithCategory(Constants.ACTIVITY_NAME));
        LOGGER.debug("Command finished");
        return Path.PAGE__VIEW_CATEGORIES;
    }
}

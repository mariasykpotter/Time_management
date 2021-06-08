package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.ActivitiesDao;
import com.example.demo.dao.Constants;
import com.example.demo.model.Person;
import com.example.demo.model.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public class DeleteActivityCommand extends Command {
    private static final long serialVersionUID = -5499050481559522685L;
    private static final Logger LOGGER = Logger.getLogger(DeleteActivityCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command started");
        String[] activities = request.getParameterValues("activities");
        int[] array = Arrays.stream(activities).mapToInt(Integer::parseInt).toArray();
        ActivitiesDao.deleteActivity(array);
        LOGGER.info("Activities removed " + Arrays.toString(activities));
        HttpSession session = request.getSession();
        session.setAttribute("activities_list", ActivitiesDao.getAllActivitiesWithCategory(Constants.ACTIVITY_NAME));
        LOGGER.info("Set the session attribute: activities_list --> " + ActivitiesDao.getAllActivitiesWithCategory(Constants.ACTIVITY_NAME));
        if (Role.getRole((Person) session.getAttribute("user")) == Role.ADMIN) {
            LOGGER.debug("Command finished");
            return Path.PAGE__ADMIN;
        }
        LOGGER.debug("Command finished");
        return Path.PAGE__ACTIVITIES;
    }
}

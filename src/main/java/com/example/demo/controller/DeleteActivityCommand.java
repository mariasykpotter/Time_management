package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.ActivityDao;
import com.example.demo.dao.DBManager;
import com.example.demo.model.Person;
import com.example.demo.model.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class DeleteActivityCommand extends Command {
    private static final long serialVersionUID = -5499050481559522685L;
    private static final Logger LOGGER = Logger.getLogger(DeleteActivityCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command started");
        HttpSession session = request.getSession();
        String[] activities = request.getParameterValues("activities");
        if (activities != null) {
            int[] array = Arrays.stream(activities).mapToInt(Integer::parseInt).toArray();
            DBManager.deleteEntity("ACTIVITY", array);
            LOGGER.info("Activities removed " + Arrays.toString(activities));
            if (Role.getRole((Person) session.getAttribute("user")) == Role.ADMIN) {
                LOGGER.debug("Command finished");
                return Path.PAGE_ADMIN;
            } else {
                LOGGER.debug("Command finished");
                return Path.PAGE_ACTIVITIES;
            }
        } else {
            String sessionAttr = (String) session.getAttribute("locale");
            ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(sessionAttr.split("_")[0], sessionAttr.split("_")[1]));
            String errorMessage = bundle.getString("error.noItemsSelected");
            session.setAttribute("errorMessage", errorMessage);
            return Path.PAGE_ERROR_PAGE;
        }
    }
}

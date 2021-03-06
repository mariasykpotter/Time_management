package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.ActivityDao;
import com.example.demo.model.Person;
import com.example.demo.model.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddActivityCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(AddActivityCommand.class);
    private static final long serialVersionUID = -951882700477310195L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command starts");
        String activityName = request.getParameter("activity");
        String categoryId = request.getParameter("category");
        LOGGER.trace("Activity" + activityName + "added: " + ActivityDao.addActivity(activityName, Integer.valueOf(categoryId)));
        HttpSession session = request.getSession();
        LOGGER.debug("Command finished");
        if (Role.getRole((Person) session.getAttribute("user")) == Role.ADMIN) {
            return Path.PAGE_ADMIN;
        }
        return Path.PAGE_ACTIVITIES;
    }
}

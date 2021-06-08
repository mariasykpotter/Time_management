package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.PersonDao;
import com.example.demo.model.Person;
import com.example.demo.model.Role;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);
    private static final long serialVersionUID = 5841167663904890042L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Command starts");
        HttpSession session = request.getSession();
        if (request.getParameter("full_name") != null && request.getParameter("user_name") != null && request.getParameter("password") != null) {
            String[] fullName = request.getParameter("full_name").split(" ");
            String userName = request.getParameter("user_name");
            String password = request.getParameter("password");
            Role role = (Role) session.getAttribute("userRole");
            String forward;
            if (role == Role.ADMIN) {
                PersonDao.insertPerson(new Person(fullName[1], fullName[0], userName, role.ordinal(), password));
                LOGGER.info(String.format("Person with role %s added", role.name()));
                forward = Path.PAGE__VIEW_USERS;
            } else {
                PersonDao.insertPerson(new Person(fullName[1], fullName[0], userName, Role.USER.ordinal(), password));
                LOGGER.info("Person with role USER added");
                forward = Path.PAGE__LOGIN;
            }
            return forward;
        } else {
            LOGGER.error("Some of the fields are null");
            return Path.PAGE__REGISTRATION;
        }
    }
}

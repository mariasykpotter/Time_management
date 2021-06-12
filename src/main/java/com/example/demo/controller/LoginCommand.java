package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.HashProcessor;
import com.example.demo.dao.PersonDao;
import com.example.demo.model.Person;
import com.example.demo.model.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");
        HttpSession session = request.getSession();
        String login = request.getParameter("user_name");
        log.trace("Request parameter: login --> " + login);
        String password = request.getParameter("password");
        String forward = Path.PAGE_ERROR_PAGE;
        // error handler
        String errorMessage;
        Person user = PersonDao.getUserByLogin(login);
        if (user == null || !HashProcessor.validatePassword(password, user.getPassword())) {
            String sessionAttr = (String) session.getAttribute("locale");
            ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(sessionAttr.split("_")[0], sessionAttr.split("_")[1]));
            errorMessage = bundle.getString("error.noSuchUser");
            session.setAttribute("errorMessage", errorMessage);
            log.trace("Set the session attribute: errorMessage --> " + errorMessage);
            return forward;
        } else {
            log.trace("Found in DB: user --> " + user);
            Role userRole = Role.getRole(user);
            log.trace("userRole --> " + userRole);
            if (userRole == Role.ADMIN) {
                forward = Path.PAGE_ADMIN;
            }

            if (userRole == Role.USER) {
                forward = Path.PAGE_ACTIVITIES;
            }

            session.setAttribute("user", user);
            log.trace("Set the session attribute: user --> " + user);

            session.setAttribute("userRole", userRole);
            log.trace("Set the session attribute: userRole --> " + userRole);
            log.info("Person " + user + " logged as " + userRole.toString());

            // work with i18n
            String userLocaleName = user.getLocaleName();
            log.trace("userLocalName --> " + userLocaleName);
            log.debug("Command finished");
            return forward;
        }
    }
}

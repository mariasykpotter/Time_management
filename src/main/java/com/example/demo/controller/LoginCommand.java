package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.ActivitiesDao;
import com.example.demo.dao.Constants;
import com.example.demo.dao.HashProcessor;
import com.example.demo.dao.PersonDao;
import com.example.demo.model.Person;
import com.example.demo.model.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

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
        String forward = Path.PAGE__ERROR_PAGE;
        // error handler
        String errorMessage;
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }
        Person user = PersonDao.getUserByLogin(login);
        if (user == null || !HashProcessor.validatePassword(password, user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        } else {
            log.trace("Found in DB: user --> " + user);
            Role userRole = Role.getRole(user);
            log.trace("userRole --> " + userRole);
            if (userRole == Role.ADMIN) {
                session.setAttribute("activities_list", ActivitiesDao.getAllActivitiesWithCategory(null));
                forward = Path.PAGE__ADMIN;
            }

            if (userRole == Role.USER) {
                session.setAttribute("activities_list", ActivitiesDao.getAllActivitiesWithCategory(Constants.ACTIVITY_NAME));
                forward = Path.PAGE__ACTIVITIES;
            }

            session.setAttribute("user", user);
            log.trace("Set the session attribute: user --> " + user);

            session.setAttribute("userRole", userRole);
            log.trace("Set the session attribute: userRole --> " + userRole);

            log.info("Person " + user + " logged as " + userRole.toString());

            // work with i18n
            String userLocaleName = user.getLocaleName();
            log.trace("userLocalName --> " + userLocaleName);

            if (userLocaleName != null && !userLocaleName.isEmpty()) {
                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);

                session.setAttribute("locale", userLocaleName);
                log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);

                log.info("Locale for user: defaultLocale --> " + userLocaleName);
            }
        }

        log.debug("Command finished");
        return forward;
    }
}

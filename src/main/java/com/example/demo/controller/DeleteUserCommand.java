package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.PersonDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class DeleteUserCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(DeleteUserCommand.class);
    private static final long serialVersionUID = -545880619700263796L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command starts");
        String[] users = request.getParameterValues("users");
        int[] array = Arrays.stream(users).mapToInt(Integer::parseInt).toArray();
        PersonDao.deletePerson(array);
        LOGGER.trace("Users removed" + users);
        LOGGER.debug("Command finished");
        return Path.PAGE__VIEW_USERS;
    }
}

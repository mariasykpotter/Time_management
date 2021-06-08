package com.example.demo.controller;

import com.example.demo.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOutCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(LogOutCommand.class);
    private static final long serialVersionUID = -3055907402043833210L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command  starts");
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();
        LOGGER.info("Session invalidated");
        LOGGER.debug("Command finished");
        return Path.PAGE__LOGIN;
    }
}

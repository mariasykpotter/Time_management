package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.TimeLogDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ApproveCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(ApproveCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command starts");
        String time_log_id = request.getParameter("time_log_id");
        TimeLogDao.updateStatus(Integer.valueOf(time_log_id));
        List<List<String>> unapproved = TimeLogDao.getInfoByStatus(time_log_id, 0);
        request.setAttribute("unapproved", unapproved);
        LOGGER.info("Set the request attribute: unapproved --> " + unapproved);
        LOGGER.debug("Command finished");
        return Path.PAGE__ADMIN;
    }
}

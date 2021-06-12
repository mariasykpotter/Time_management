package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.ActivityDao;
import com.example.demo.dao.TimeLogDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;

public class EditTimeLogCommand extends Command {
    private static final long serialVersionUID = 4562543430059372529L;
    private static final Logger LOGGER = Logger.getLogger(EditTimeLogCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       LOGGER.debug("Command starts");
       LOGGER.trace("Update timelog set activity: {}, start_at: {}, end_at: {}".format(request.getParameter("activity"),request.getParameter("start_at"),request.getParameter("end_at")));
        TimeLogDao.updateTimeLog(ActivityDao.getIdByName(request.getParameter("activity")), Time.valueOf(request.getParameter("start_at")),
                Time.valueOf(request.getParameter("end_at")), Integer.valueOf(request.getParameter("log_id")));
        LOGGER.debug("Command finished");
        return Path.PAGE_EDIT_TIMELOGS;
    }
}

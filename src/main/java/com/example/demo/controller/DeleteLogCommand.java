package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.DBManager;
import com.example.demo.dao.TimeLogDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class DeleteLogCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(DeleteLogCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command starts");
        String[] timelogs = request.getParameterValues("timelogs");
        int[] array = Arrays.stream(timelogs).mapToInt(Integer::parseInt).toArray();
        DBManager.deleteEntity("TIME_LOG",array);
        LOGGER.trace("Categories removed" + Arrays.toString(timelogs));
        LOGGER.debug("Command finished");
        return Path.PAGE_ADMIN;
    }
}

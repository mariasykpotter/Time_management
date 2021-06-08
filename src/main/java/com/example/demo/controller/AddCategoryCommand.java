package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.CategoryDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCategoryCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(AddCategoryCommand.class);
    private static final long serialVersionUID = -4090452643793095551L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command starts");
        String categoryName = request.getParameter("categoryName");
        CategoryDao.addCategory(categoryName);
        LOGGER.trace("Category added -->" + categoryName);
        LOGGER.debug("Command finished");
        return Path.PAGE__VIEW_CATEGORIES;
    }
}

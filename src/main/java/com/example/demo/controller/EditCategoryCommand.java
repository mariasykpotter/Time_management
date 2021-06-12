package com.example.demo.controller;

import com.example.demo.Path;
import com.example.demo.dao.CategoryDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditCategoryCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(EditCategoryCommand.class);
    private static final long serialVersionUID = -4101976219133152161L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command starts");
        String categoryId = request.getParameter("category_id");
        String category = request.getParameter("category");
        CategoryDao.updateCategory(Integer.valueOf(request.getParameter("category_id")), category);
        LOGGER.trace("Category with id" + categoryId + " updated to category " + category);
        LOGGER.debug("Command finished");
        return Path.PAGE_VIEW_CATEGORIES;
    }
}

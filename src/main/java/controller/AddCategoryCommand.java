package controller;

import com.example.demo.dao.CategoryDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCategoryCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CategoryDao.addCategory(request.getParameter("categoryName"));
        return "/WEB-INF/view_categories.jsp";
    }
}

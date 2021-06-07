package controller;

import com.example.demo.dao.ActivitiesDao;
import com.example.demo.dao.CategoryDao;
import com.example.demo.dao.Constants;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public class DeleteCategoryCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(DeleteCategoryCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command starts");
        String[] activities = request.getParameterValues("categories");
        LOGGER.trace("Activities to delete" + activities);
        int[] array = Arrays.asList(activities).stream().mapToInt(Integer::parseInt).toArray();
        CategoryDao.deleteCategory(array);
        HttpSession session = request.getSession();
        session.setAttribute("activities_list", ActivitiesDao.getAllActivitiesWithCategory(Constants.ACTIVITY_NAME));
        LOGGER.info("Set the session attribute: activities_list --> " + ActivitiesDao.getAllActivitiesWithCategory(Constants.ACTIVITY_NAME));
        return "/WEB-INF/view_categories.jsp";
    }
}

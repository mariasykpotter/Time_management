package controller;

import com.example.demo.dao.ActivitiesDao;
import com.example.demo.dao.CategoryDao;
import model.Person;
import model.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddActivityCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(AddActivityCommand.class);
    private static final long serialVersionUID = -951882700477310195L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command starts");
        String activityName = request.getParameter("activity");
        String categoryId = request.getParameter("category");
        LOGGER.trace("Value of category id: " + categoryId);
        LOGGER.trace("Activity added: " + ActivitiesDao.addActivity(activityName, Integer.valueOf(categoryId)));
        HttpSession session = request.getSession();
        session.setAttribute("activities_list", ActivitiesDao.getAllActivitiesWithCategory());
        LOGGER.info("Set the session attribute: activities_list --> " + ActivitiesDao.getAllActivitiesWithCategory());
        LOGGER.debug("Command finished");
        if (Role.getRole((Person) session.getAttribute("user")) == Role.ADMIN) {
            return "admin.jsp";
        }
        return "activities.jsp";
    }
}

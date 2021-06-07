package controller;

import com.example.demo.dao.ActivitiesDao;
import com.example.demo.dao.Constants;
import model.Person;
import model.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteActivityCommand extends Command {
    private static final long serialVersionUID = -5499050481559522685L;
    private static final Logger LOGGER = Logger.getLogger(DeleteActivityCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command DeleteActivity started");
        String[] activities = request.getParameterValues("activities");
        int[] array = Arrays.asList(activities).stream().mapToInt(Integer::parseInt).toArray();
        ActivitiesDao.deleteActivity(array);
        LOGGER.info("Activity removed");
        HttpSession session = request.getSession();
        session.setAttribute("activities_list", ActivitiesDao.getAllActivitiesWithCategory(Constants.ACTIVITY_NAME));
        LOGGER.info("Set the session attribute: activities_list --> " + ActivitiesDao.getAllActivitiesWithCategory(Constants.ACTIVITY_NAME));
        if (Role.getRole((Person) session.getAttribute("user")) == Role.ADMIN) {
            return "/WEB-INF/admin.jsp";
        }
        return "/WEB-INF/activities.jsp";
    }
}

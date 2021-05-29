package controller;

import com.example.demo.dao.ActivitiesDao;
import model.Person;
import model.Role;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteActivityCommand extends Command{
    private static final long serialVersionUID = -5499050481559522685L;
    private static final Logger LOGGER = Logger.getLogger(DeleteActivityCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.debug("Command DeleteActivity started");
        ActivitiesDao.deleteActivity(Integer.valueOf(request.getParameter("id")));
        LOGGER.info("Activity removed");
        HttpSession session = request.getSession();
        session.setAttribute("activities_list",ActivitiesDao.getAllActivitiesWithCategory());
        LOGGER.info("Set the session attribute: activities_list --> " + ActivitiesDao.getAllActivitiesWithCategory());
        if (Role.getRole((Person) session.getAttribute("user")) == Role.ADMIN) {
            return "admin.jsp";
        }
        return "activities.jsp";
    }
}

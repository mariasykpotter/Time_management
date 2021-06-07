package controller;

import com.example.demo.dao.ActivitiesDao;
import com.example.demo.dao.TimeLogDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;

public class EditTimeLogCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        TimeLogDao.updateTimeLog(ActivitiesDao.getIdByName(request.getParameter("activity")), Time.valueOf(request.getParameter("start_at")),
                Time.valueOf(request.getParameter("end_at")), Integer.valueOf(request.getParameter("log_id")));
        return "/WEB-INF/edit_timelogs.jsp";
    }
}

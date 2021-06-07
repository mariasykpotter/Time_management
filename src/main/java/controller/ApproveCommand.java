package controller;

import com.example.demo.dao.TimeLogDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ApproveCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String time_log_id = request.getParameter("time_log_id");
        TimeLogDao.updateStatus(Integer.valueOf(time_log_id));
        List<List<String>> unapproved = TimeLogDao.getInfoByStatus(time_log_id, 0);
        request.setAttribute("unapproved", unapproved);
        return "/WEB-INF/admin.jsp";
    }
}

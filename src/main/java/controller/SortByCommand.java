package controller;

import com.example.demo.dao.ActivitiesDao;
import com.example.demo.dao.Constants;
import com.example.demo.dao.TimeLogDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SortByCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String param = request.getParameter("name");
        List<List<String>> activities;
        if(param.equals("users_number")) {
            activities = TimeLogDao.getOrderedQuantityPerActivity();
        }else{
            activities = ActivitiesDao.getOrderedActivitiesWithCategory(param);
        }
        session.setAttribute("activities_list", activities);
        return "admin.jsp";
    }
}

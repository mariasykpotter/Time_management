package controller;

import com.example.demo.dao.TimeLogDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewUnapproved extends Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer id = Integer.valueOf(request.getParameter("id"));
        List<List<String>> unapproved = TimeLogDao.getInfoByStatus(id,0);
        request.setAttribute("unapproved",unapproved);
        return "unapproved.jsp";
    }
}

package controller;

import com.example.demo.dao.PersonDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer userId = Integer.valueOf(request.getParameter("user_id"));
        PersonDao.deletePerson(userId);
        return "view_users.jsp";
    }
}

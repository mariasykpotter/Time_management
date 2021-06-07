package controller;
import com.example.demo.dao.PersonDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class DeleteUserCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] activities = request.getParameterValues("users");
        int[] array = Arrays.asList(activities).stream().mapToInt(Integer::parseInt).toArray();
        PersonDao.deletePerson(array);
        return "/WEB-INF/view_users.jsp";
    }
}

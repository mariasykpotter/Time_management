package controller;

import com.example.demo.dao.PersonDao;
import model.Person;
import model.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterCommand extends Command {
    private static final long serialVersionUID = 5841167663904890042L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (request.getParameter("full_name") != null && request.getParameter("user_name") != null && request.getParameter("password") != null) {
            String[] fullName = request.getParameter("full_name").split(" ");
            String userName = request.getParameter("user_name");
            String password = request.getParameter("password");
            Role role = (Role) session.getAttribute("userRole");
            if (role == Role.ADMIN) {
                PersonDao.insertPerson(new Person(fullName[1], fullName[0], userName, role.ordinal(), password));
                return "/WEB-INF/view_users.jsp";
            } else {
                PersonDao.insertPerson(new Person(fullName[1], fullName[0], userName, Role.CLIENT.ordinal(), password));
                return "/WEB-INF/index.jsp";
            }
        } else {
            return "WEB-INF/registration.jsp";
        }
    }
}


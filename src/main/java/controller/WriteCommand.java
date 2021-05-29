package controller;

import com.example.demo.dao.ActivitiesDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class WriteCommand extends Command {
    private static final Logger logger = Logger.getLogger(WriteCommand.class);
    private static final long serialVersionUID = -5376932737039481399L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Command Write started");
        HttpSession session = request.getSession();
        session.setAttribute("activity", ActivitiesDao.getById(Integer.valueOf(request.getParameter("id"))));
        logger.info("Activity added to user session");
        return "write.jsp";
    }
}

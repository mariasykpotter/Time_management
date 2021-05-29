package controller;

import com.example.demo.dao.TimeLogDao;
import model.Activity;
import model.Person;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddLogCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddLogCommand.class);
    private static final String MESSAGE = "End time should be after start time";
    private static final long serialVersionUID = 3122281700814233499L;

    public static float countDifference(String time1, String time2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (float) ((date2.getTime() - date1.getTime()) / (60 * 1000 * 60.0));
    }

    public static boolean validateTime(String time1, String time2) {
        Pattern p = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})");
        Matcher m1 = p.matcher(time1);
        Matcher m2 = p.matcher(time2);
        if (m1.matches() && m2.matches() && Integer.parseInt(m1.group(1)) <= Integer.parseInt(m2.group(1)) && Integer.parseInt(m1.group(2)) <= Integer.parseInt(m2.group(2))) {
            return true;
        }
        return false;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Command Write started");
        HttpSession session = request.getSession();
        Person user = (Person) session.getAttribute("user");
        Activity activity = (Activity) session.getAttribute("activity");
        String time1 = request.getParameter("start") + ":00";
        String time2 = request.getParameter("end") + ":00";
        if (validateTime(time1, time2)) {
            TimeLogDao.addTimeLog(user.getId(), activity.getId(), Time.valueOf(time1), Time.valueOf(time2), countDifference(time1, time2));
            logger.info("Time log added");
            logger.debug("Command Write finished");
            return "activities.jsp";
        } else {
            logger.info("Error occurred");
            session.setAttribute("message", MESSAGE);
            logger.trace("Set the session attribute: message --> " + MESSAGE);
            logger.debug("Command Write finished");
            return "write.jsp";
        }
    }
}

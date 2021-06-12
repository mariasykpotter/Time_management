package com.example.demo.filter;

import com.example.demo.Path;
import com.example.demo.model.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Filters the request by the rights of users.
 */
public class CommandAccessFilter implements Filter {

    private static final Logger log = Logger.getLogger(CommandAccessFilter.class);
    private static final String LOCALE = "locale";

    // commands access
    private static final Map<Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();

    @Override
    public void destroy() {
        log.debug("Filter destruction starts");
        // do nothing
        log.debug("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("Filter starts");
        log.trace("Access--> " + accessAllowed(request));
        if (accessAllowed(request)) {
            log.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession session = httpRequest.getSession();
            log.trace(LOCALE + session.getAttribute(LOCALE));
            String sessionAttr = (String) session.getAttribute(LOCALE);
            ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(sessionAttr.split("_")[0], sessionAttr.split("_")[1]));
            String errorMessage = bundle.getString("error.accessDenied");
            session.setAttribute("errorMessage", errorMessage);
            log.trace("Set the request attribute: errorMessage --> " + errorMessage);

            request.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
                    .forward(request, response);
        }
    }

    /**
     * Defined whether or not the request to a particular page is allowed.
     *
     * @param request HTTP request
     * @return boolean whether access is allowed to a page.
     */
    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.trace("URL: " + ((HttpServletRequest) request).getRequestURI().replace("/", ""));
        String endPoint = ((HttpServletRequest) request).getRequestURI().replace("/", "");
        String commandName;
        if (endPoint.equals("controller")) {
            commandName = request.getParameter("command");
        } else {
            commandName = endPoint;
        }
        log.trace("Received command --> " + commandName);
        if (commandName == null || commandName.isEmpty())
            return false;

        if (outOfControl.contains(commandName))
            return true;

        HttpSession session = httpRequest.getSession(false);
        if (session == null)
            return false;

        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole == null)
            return false;
        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }

    @Override
    public void init(FilterConfig fConfig) {

        log.debug("Filter initialization starts");

        // roles
        accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
        accessMap.put(Role.USER, asList(fConfig.getInitParameter("user")));
        log.trace("Access map --> " + accessMap);

        // commons
        commons = asList(fConfig.getInitParameter("common"));
        log.trace("Common commands --> " + commons);

        // out of control
        outOfControl = asList(fConfig.getInitParameter("out-of-control"));
        log.trace("Out of control commands --> " + outOfControl);

        log.debug("Filter initialization finished");
    }

    /**
     * Extracts parameter values from string.
     *
     * @param str parameter values string.
     * @return list of parameter values.
     */
    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }
}

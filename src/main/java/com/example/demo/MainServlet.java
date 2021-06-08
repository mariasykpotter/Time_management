package com.example.demo;

import com.example.demo.controller.Command;
import com.example.demo.controller.CommandContainer;
import org.apache.log4j.Logger;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "MainServlet", value = "/controller")
public class MainServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(HelloServlet.class));

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response, "GET");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response, "POST");
    }

    public void process(HttpServletRequest request, HttpServletResponse response, String method) throws IOException, ServletException {
        {
            response.setContentType("text/html; charset=utf-8");
            LOGGER.info("Controller starts");

            // extract command name from the request
            String commandName = request.getParameter("command");
            LOGGER.info("Request parameter: command --> " + commandName);

            // obtain command object by its name
            Command command = CommandContainer.get(commandName);
            LOGGER.info("Obtained command --> " + command);

            // execute command and get forward address
            String forward = null;
            try {
                forward = command.execute(request, response);
            } catch (IOException | ServletException e) {
                LOGGER.error(e.getMessage());
            }
            LOGGER.info("Forward address --> " + forward);
            LOGGER.info("Controller finished, now go to forward address --> " + forward);
            // if the forward address is not null go to the address
            if (forward != null && method.equals("GET")) {
                request.getRequestDispatcher(forward).forward(request, response);
            } else if (forward != null) {
                response.sendRedirect(forward);
            }
        }
    }
}


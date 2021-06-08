package com.example.demo.controller;


import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private CommandContainer() {
    }

    private static final Logger LOGGER = Logger.getLogger(CommandContainer.class);
    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        commands.put("register", new RegisterCommand());
        commands.put("login", new LoginCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("addLog", new AddLogCommand());
        commands.put("addActivity", new AddActivityCommand());
        commands.put("addCategory", new AddCategoryCommand());
        commands.put("approve", new ApproveCommand());
        commands.put("editCategory", new EditCategoryCommand());
        commands.put("editTimelog", new EditTimeLogCommand());
        commands.put("deleteActivity", new DeleteActivityCommand());
        commands.put("deleteUser", new DeleteUserCommand());
        commands.put("deleteCategory", new DeleteCategoryCommand());
        commands.put("logout", new LogOutCommand());
        LOGGER.debug("Command container was successfully initialized");
        LOGGER.trace("Number of commands --> " + commands.size());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOGGER.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}


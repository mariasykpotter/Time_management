package controller;


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
        commands.put("write", new WriteCommand());
        commands.put("add_log", new AddLogCommand());
        commands.put("add_activity", new AddActivityCommand());
        commands.put("view_unapproved", new ViewUnapproved());
        commands.put("approve", new ApproveCommand());
        commands.put("sort_by", new SortByCommand());
        commands.put("edit_timelog", new EditTimeLogCommand());
        commands.put("delete_activity", new DeleteActivityCommand());
        commands.put("delete_user", new DeleteUserCommand());
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


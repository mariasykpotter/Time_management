package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public abstract class Command implements Serializable {
    private static final long serialVersionUID = 8879403039606311780L;

    public Command() { /* compiled code */ }

    public abstract String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException;

    @Override
    public final String toString(){
        return getClass().getSimpleName();
    }
}

package com.example.demo;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class ListTag extends TagSupport {
    private static final long serialVersionUID = -2067023181560522212L;
    private String num;
    private List<List<String>> list;

    public void setNum(String number) {
        this.num = number;
    }

    public void setList(List<List<String>> lst) {
        this.list = lst;
    }

    @Override
    public int doStartTag() {
        JspWriter writer = pageContext.getOut();
        try {
            int num1 = Integer.parseInt(num);
            for (int i = 0; i <= num1; i++) {
                writer.println("<td>" + list.get(i) + "</td>\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}

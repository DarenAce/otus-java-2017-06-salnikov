package ru.otus.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class CacheMonitorServlet extends HttpServlet {
    public static final String AUTHORIZED_USER_NAME = "admin";
    private static final String ADMIN_PAGE_TEMPLATE = "monitor.html";

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String login = null;

        login = (String) request.getSession().getAttribute("login");
        if (login == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("login")) {
                        login = cookie.getValue();
                    }
                }
            }
        }

        if (AUTHORIZED_USER_NAME.equals(login)) {
            Map<String, Object> pageVariables = new HashMap<>();

            response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendRedirect("/login");
        }
    }
}

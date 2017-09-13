package ru.otus.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class LoginServlet extends HttpServlet {

    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String MESSAGE_VARIABLE_NAME = "message";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";

    private Map<String, String> loginToPassword;

    public LoginServlet() {
        loginToPassword = new HashMap<>();
        loginToPassword.put("admin", "admin");
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);
        String requestPassword = request.getParameter(PASSWORD_PARAMETER_NAME);

        if (requestLogin == null || requestLogin.equals("")) {
            String page = getPage("Authorize to continue.");
            response.getWriter().println(page);
            setOK(response);
            return;
        }

        if (loginToPassword.containsKey(requestLogin) && loginToPassword.get(requestLogin).equals(requestPassword)) {
            saveToSession(request, requestLogin);
            saveToCookie(response, requestLogin);
            response.sendRedirect("/monitor");
        } else {
            String page = getPage("Incorrect login or password.");
            response.getWriter().println(page);
            setOK(response);
        }
    }

    private static String getPage(String message) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(MESSAGE_VARIABLE_NAME, message == null ? "" : message);
        return TemplateProcessor.instance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    private void saveToCookie(HttpServletResponse response, String login) {
        Cookie loginCookie = new Cookie("login", login);
        loginCookie.setPath("\\*");
        loginCookie.setMaxAge(-1);
        response.addCookie(new Cookie("login", login));
    }

    private void saveToSession(HttpServletRequest request, String requestLogin) {
        request.getSession().setAttribute("login", requestLogin);
    }

    private void saveToVariable(String login, String password) {
        loginToPassword.put(login, password);
    }

    private void setOK(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

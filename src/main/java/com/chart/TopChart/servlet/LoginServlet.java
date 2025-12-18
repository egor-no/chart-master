package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.UserDAOImpl;
import com.chart.TopChart.data.model.User;
import com.chart.TopChart.web.SessionKeys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SessionKeys.USER_ID) != null) {
            response.sendRedirect("/profile");
            return;
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null) login = "";
        if (password == null) password = "";

        User u = UserDAOImpl.getByLogin(login.trim());

        if (u == null || u.getPassword() == null || !u.getPassword().equals(password)) {
            request.setAttribute("error", "Неверный логин или пароль");
            request.setAttribute("login", login);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionKeys.USER_ID, u.getId());
        session.setAttribute(SessionKeys.CHART_INFO_ID, 1);

        response.sendRedirect("/profile");
    }
}
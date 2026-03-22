package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.UserDAOImpl;
import com.chart.TopChart.data.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "register", value = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");

        String login = trim(request.getParameter("login"));
        String password = trim(request.getParameter("password"));
        String nickname = trim(request.getParameter("nickname"));
        String slogan = trim(request.getParameter("slogan"));
        String bio = trim(request.getParameter("bio"));
        String avatar = trim(request.getParameter("avatar"));

        boolean hasError = false;

        if (isBlank(login)) {
            request.setAttribute("loginError", "Login is required.");
            hasError = true;
        }

        if (isBlank(password)) {
            request.setAttribute("passwordError", "Password is required.");
            hasError = true;
        }

        if (isBlank(nickname)) {
            request.setAttribute("nicknameError", "Nickname is required.");
            hasError = true;
        }

        if (!isBlank(login) && UserDAOImpl.getByLogin(login) != null) {
            request.setAttribute("loginError", "This login already exists.");
            hasError = true;
        }

        if (hasError) {
            request.setAttribute("formLogin", login);
            request.setAttribute("formNickname", nickname);
            request.setAttribute("formSlogan", slogan);
            request.setAttribute("formBio", bio);
            request.setAttribute("formAvatar", avatar);

            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setSlogan(slogan);
        user.setBio(bio);
        user.setAvatar(avatar);

        int id = UserDAOImpl.save(user);

        HttpSession session = request.getSession();
        session.setAttribute("userId", id);
        session.setAttribute("userLogin", login);

        response.sendRedirect("/profile");
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
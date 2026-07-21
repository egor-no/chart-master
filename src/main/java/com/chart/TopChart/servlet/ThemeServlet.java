package com.chart.TopChart.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/theme")
public class ThemeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        String theme = request.getParameter("name");

        if (!"magazine".equals(theme)) {
            theme = "win95";
        }

        request.getSession().setAttribute("theme", theme);

        String referer = request.getHeader("Referer");

        response.sendRedirect(
                referer != null
                        ? referer
                        : request.getContextPath() + "/"
        );
    }
}
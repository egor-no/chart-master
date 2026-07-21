package com.chart.TopChart.servlet;

import com.chart.TopChart.web.SessionKeys;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "logout", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute(SessionKeys.USER_ID);
            session.removeAttribute(SessionKeys.USER_LOGIN);
            session.removeAttribute(SessionKeys.CHART_INFO_ID);

            session.removeAttribute(SessionKeys.OWNER_CI_ID);
            session.removeAttribute(SessionKeys.OWNER_FLAG);
            session.removeAttribute(SessionKeys.CHART_TITLE);
            session.removeAttribute(SessionKeys.CHART_AUTHOR_NAME);
        }

        response.sendRedirect(request.getContextPath() + "/");
    }
}

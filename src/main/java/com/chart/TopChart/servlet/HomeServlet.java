package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.ChartInfoDAOImpl;
import com.chart.TopChart.data.dao.UserDAOImpl;
import com.chart.TopChart.web.SessionKeys;
import com.chart.TopChart.web.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "home", value = "/")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setAttribute("users", UserDAOImpl.getAllWithChartsCount());
        request.setAttribute("chartInfos", ChartInfoDAOImpl.getAllForHome());

        request.setAttribute("latest", ChartDAOImpl.getLatestForHome(12));

        request.setAttribute("totalUsers", UserDAOImpl.getTotalUsersCount());
        request.setAttribute("totalChartInfos", ChartInfoDAOImpl.getTotalChartInfosCount());
        request.setAttribute("totalCharts", ChartDAOImpl.getTotalChartsCount());

        HttpSession s = request.getSession(false);
        request.setAttribute("loggedIn", s != null && s.getAttribute(SessionKeys.USER_ID) != null);

        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
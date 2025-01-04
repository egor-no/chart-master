package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.service.ChartService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "chart", value = "/")
public class ChartServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Chart chart;

        String chartDate = request.getParameter("date");
        try {
            long chartNumber = ChartDAOImpl.getLastByDate(chartDate);
            chart = ChartDAOImpl.getById(chartNumber);
            if (chart == null) {
                throw new Exception();
            }
        } catch (Exception ex) {
            chart = ChartDAOImpl.getById(ChartDAOImpl.getLastId());
        }

        request.setAttribute("chart", ChartService.getChartFull(chart));
        request.getRequestDispatcher("main.jsp").forward(request, response);
        response.flushBuffer();
    }
}
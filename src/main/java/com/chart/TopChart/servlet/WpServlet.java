package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.service.WpService;
import com.chart.TopChart.web.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "wpexport", value = "/wp-export")
public class WpServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int chartInfoId = SessionUtil.requireChartInfoId(request);

        Chart chart = null;

        String chartNumberStr = request.getParameter("chartNumber");
        if (chartNumberStr != null && !chartNumberStr.isEmpty()) {
            try {
                long chartNumber = Long.parseLong(chartNumberStr);
                chart = ChartDAOImpl.getById(chartInfoId, chartNumber);
            } catch (Exception ignored) {}
        }

        if (chart == null) {
            Long lastId = ChartDAOImpl.getLastId(chartInfoId);
            if (lastId != null) {
                chart = ChartDAOImpl.getById(chartInfoId, lastId);
            }
        }

        if (chart == null) {
            response.sendError(404, "Chart not found");
            return;
        }

        request.setAttribute("code", WpService.formWpCode(chart));
        request.getRequestDispatcher("wp.jsp").forward(request, response);
        response.flushBuffer();
    }
}
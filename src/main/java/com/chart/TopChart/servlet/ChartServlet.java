package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.service.ChartService;
import com.chart.TopChart.web.SessionKeys;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "chart", value = "/chart")
public class ChartServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer chartInfoId = null;

        String ciStr = request.getParameter("ci");
        if (ciStr != null && !ciStr.isEmpty()) {
            try {
                chartInfoId = Integer.parseInt(ciStr);
            } catch (Exception ignored) {}
        }

        if (chartInfoId == null) {
            HttpSession s0 = request.getSession(false);
            Object v = (s0 == null) ? null : s0.getAttribute(SessionKeys.CHART_INFO_ID);
            if (v instanceof Integer) chartInfoId = (Integer) v;
        }

        if (chartInfoId == null) {
            response.sendRedirect("/profile");
            return;
        }

        HttpSession s = request.getSession(true);
        s.setAttribute(SessionKeys.CHART_INFO_ID, chartInfoId);
        s.removeAttribute(SessionKeys.OWNER_CI_ID);
        s.removeAttribute(SessionKeys.OWNER_FLAG);

        Chart chart = null;

        String chartDate = request.getParameter("date");
        if (chartDate != null && !chartDate.isEmpty()) {
            long chartId = ChartDAOImpl.getLastByDate(chartDate, chartInfoId);
            if (chartId > 0) {
                chart = ChartDAOImpl.getById(chartInfoId, chartId);
            }
        }

        if (chart == null) {
            String chartNumberStr = request.getParameter("chartNumber");
            if (chartNumberStr != null && !chartNumberStr.isEmpty()) {
                try {
                    long chartId = Long.parseLong(chartNumberStr);
                    chart = ChartDAOImpl.getById(chartInfoId, chartId);
                } catch (Exception ignored) {
                }
            }
        }

        Long lastId = ChartDAOImpl.getLastId(chartInfoId);
        if (chart == null) {
            if (lastId != null) {
                chart = ChartDAOImpl.getById(chartInfoId, lastId);
            }
        }

        if (chart == null) {
            response.sendError(404, "Chart not found");
            return;
        }

        boolean isLastChart = (lastId != null && chart.getId() == lastId);

        request.setAttribute("chart", ChartService.getChartFull(chart));
        request.setAttribute("isLastChart", isLastChart);
        request.getRequestDispatcher("chart.jsp").forward(request, response);
        response.flushBuffer();
    }
}
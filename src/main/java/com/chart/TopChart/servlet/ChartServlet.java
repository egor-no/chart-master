package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.Position;
import com.chart.TopChart.data.model.Song;
import com.chart.TopChart.service.PositionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "chart", value = "/")
public class ChartServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Chart chart;
        String chartNumberStr = request.getParameter("chart");
        try {
            long chartNumber = Long.parseLong(chartNumberStr);
            chart = ChartDAOImpl.getById(chartNumber);
        } catch (Exception ex) {
            chart = ChartDAOImpl.getById(ChartDAOImpl.getLastId());
        }

        request.setAttribute("chart", chart);
        request.setAttribute("woc", PositionService.getWOCforChart(chart));
        request.setAttribute("peaks", PositionService.getPeaksForChart(chart));
        request.getRequestDispatcher("main.jsp").forward(request, response);
        response.flushBuffer();
    }
}
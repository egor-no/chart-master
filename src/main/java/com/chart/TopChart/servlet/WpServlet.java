package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.service.ArtistSongService;
import com.chart.TopChart.service.WpService;

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
        Chart chart;
        try {
            long chartNumber = Long.parseLong(request.getParameter("chartNumber"));
            chart = ChartDAOImpl.getById(chartNumber);
        } catch (Exception ex2) {
            chart = ChartDAOImpl.getById(ChartDAOImpl.getLastId());
        }

        request.setAttribute("code", WpService.formWpCode(chart));
        request.getRequestDispatcher("wp.jsp").forward(request, response);
        response.flushBuffer();
    }
}
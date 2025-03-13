package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dto.ChartRun;
import com.chart.TopChart.data.dto.SongHistory;
import com.chart.TopChart.service.SongHistoryService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "songhistory", value = "/songhistory")
public class SongHistoryServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long idSong = Long.parseLong(request.getParameter("idSong"));
        Long currentChart;
        try {
            currentChart = Long.parseLong(request.getParameter("chartNumber"));
        } catch (Exception ex) {
            currentChart = ChartDAOImpl.getLastId();
        }
        SongHistory songHistory  = SongHistoryService.getSongHistory(idSong);
        songHistory.setCurrentChart(currentChart);

        String json = new Gson().toJson(songHistory);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
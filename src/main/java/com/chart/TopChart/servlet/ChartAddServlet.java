package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.Position;
import com.chart.TopChart.data.model.Position_PK;
import com.chart.TopChart.data.model.Song;
import com.chart.TopChart.service.ChartService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "chartAdd", value = "/chartadd")
public class ChartAddServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String json = new Gson().toJson(SongDAOImpl.getAll());
        request.setAttribute("songs", json);
        request.getRequestDispatcher("chartadd.jsp").forward(request, response);
        response.flushBuffer();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String ids[] = request.getParameterValues("idSong[]");
        String artists[] = request.getParameterValues("artists[]");
        String name[] = request.getParameterValues("name[]");

        ChartService.formChart(ids, name, artists);

        response.sendRedirect("/");
        response.flushBuffer();
    }
}
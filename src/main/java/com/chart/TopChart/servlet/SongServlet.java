package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Song;
import com.chart.TopChart.web.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "songs", value = "/songs")
public class SongServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int chartInfoId = SessionUtil.requireChartInfoId(request);

        String searchPhrase = request.getParameter("search");

        List<Song> songs;
        if (searchPhrase != null && !searchPhrase.isEmpty()) {
            songs = SongDAOImpl.getBySearchPhrase(chartInfoId, searchPhrase);
        } else {
            songs = new ArrayList<>();
        }

        request.setAttribute("songs", songs);
        request.setAttribute("search", searchPhrase);
        request.getRequestDispatcher("songs.jsp").forward(request, response);
        response.flushBuffer();
    }
}
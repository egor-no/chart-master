package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.dto.ArtistSongRow;
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
        SessionUtil.ensureChartContext(request, chartInfoId);

        String searchPhrase = request.getParameter("search");

        List<ArtistSongRow> songRows;
        List<Song> songs;

        if (searchPhrase != null && !searchPhrase.isEmpty()) {
            songRows = SongDAOImpl.getSongRowsBySearchPhrase(chartInfoId, searchPhrase);

            songs = new ArrayList<>();
            for (ArtistSongRow row : songRows) {
                songs.add(row.getSong());
            }
        } else {
            songRows = new ArrayList<>();
            songs = new ArrayList<>();
        }

        request.setAttribute("songRows", songRows);
        request.setAttribute("songs", songs);
        request.setAttribute("search", searchPhrase);
        request.getRequestDispatcher("songs.jsp").forward(request, response);
        response.flushBuffer();
    }
}
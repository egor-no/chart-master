package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.web.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "artists", value = "/artists")
public class ArtistsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int chartInfoId = SessionUtil.requireChartInfoId(request);

        String searchPhrase = request.getParameter("search");
        List<String> artists;

        if (searchPhrase != null && !searchPhrase.isEmpty()) {
            artists = SongDAOImpl.getArtistsBySearch(chartInfoId, searchPhrase);
        } else {
            artists = SongDAOImpl.getArtists(chartInfoId);
        }

        request.setAttribute("artists", artists);
        request.setAttribute("search", searchPhrase);
        request.getRequestDispatcher("artists.jsp").forward(request, response);
        response.flushBuffer();
    }
}
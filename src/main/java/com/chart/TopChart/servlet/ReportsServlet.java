package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.service.ArtistSongService;
import com.chart.TopChart.service.ChartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "reports", value = "/reports")
public class ReportsServlet extends HttpServlet  {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String report = request.getParameter("report");
        if (report == null || report.isEmpty()) {
            request.getRequestDispatcher("reports.jsp").forward(request, response);
        } else if (report.equalsIgnoreCase("longestSongs")) {
            request.setAttribute("songs", SongDAOImpl.getLongestSongs());
            request.getRequestDispatcher("reports/longest-songs.jsp").forward(request, response);
        } else if (report.equalsIgnoreCase("no1Songs")) {
            request.setAttribute("songs", SongDAOImpl.getLongestNo1Songs());
            request.getRequestDispatcher("reports/no1-songs.jsp").forward(request, response);
        } else if (report.equalsIgnoreCase("topSongs")) {
            request.setAttribute("songs", SongDAOImpl.getBiggestScoreSongs());
            request.getRequestDispatcher("reports/top-songs.jsp").forward(request, response);
        } else if(report.equalsIgnoreCase("topArtists")) {
            request.setAttribute("artists", ArtistSongService.getTopArtistsBySongs());
            request.getRequestDispatcher("reports/top-artists.jsp").forward(request, response);
        }
        response.flushBuffer();

    }
}

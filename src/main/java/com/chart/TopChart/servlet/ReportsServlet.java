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
import java.util.ArrayList;

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
        } else if (report.equalsIgnoreCase("topSongsDate")) {
            String sDate1 = request.getParameter("date1");
            String sDate2 = request.getParameter("date2");

            if (sDate1 == null && sDate2 == null || sDate1.isEmpty()) {
                request.setAttribute("isListLoaded", false);
                request.setAttribute("songs", new ArrayList<>());
            } else {
                request.setAttribute("isListLoaded", true);
                request.setAttribute("date1", sDate1);
                request.setAttribute("date2", sDate2);
                request.setAttribute("songs", SongDAOImpl.getBiggestScoreSongsByDate(sDate1, sDate2));
            }
            request.getRequestDispatcher("reports/top-songs-date.jsp").forward(request, response);
        } else if(report.equalsIgnoreCase("effectiveArtists")) {
            request.setAttribute("artists", ArtistSongService.getTopArtistsBySongs());
            request.getRequestDispatcher("reports/effective-artists.jsp").forward(request, response);
        } else if(report.equalsIgnoreCase("topArtists")) {
            request.setAttribute("artists", ArtistSongService.getTopArtists());
            request.getRequestDispatcher("reports/top-artists.jsp").forward(request, response);
        } else if (report.equalsIgnoreCase("topArtistsDate")) {
            String sDate1 = request.getParameter("date1");
            String sDate2 = request.getParameter("date2");

            if (sDate1 == null && sDate2 == null || sDate1.isEmpty()) {
                request.setAttribute("isListLoaded", false);
                request.setAttribute("artists", new ArrayList<>());
            } else {
                request.setAttribute("isListLoaded", true);
                request.setAttribute("date1", sDate1);
                request.setAttribute("date2", sDate2);
                request.setAttribute("artists", ArtistSongService.getTopArtistsByDate(sDate1, sDate2));
            }
            request.getRequestDispatcher("reports/top-artists-date.jsp").forward(request, response);
        }
        response.flushBuffer();

    }
}

package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.service.ArtistSongService;
import com.chart.TopChart.service.ChartService;
import com.chart.TopChart.service.ReportService;
import com.chart.TopChart.web.SessionUtil;

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
        Integer chartInfoIdObj = SessionUtil.resolveChartInfoId(request);

        if (chartInfoIdObj == null) {
            response.sendRedirect("/");
            return;
        }
        int chartInfoId = chartInfoIdObj;

        String report = request.getParameter("report");
        if (report == null || report.isEmpty()) {
            request.getRequestDispatcher("reports.jsp").forward(request, response);

        } else if (report.equalsIgnoreCase("longestSongs")) {
            request.setAttribute("songs", SongDAOImpl.getLongestSongs(chartInfoId));
            request.setAttribute("currentlyChartingSongs", ReportService.getCurrentlyChartingMap(chartInfoId));
            request.getRequestDispatcher("reports/longest-songs.jsp").forward(request, response);

        } else if (report.equalsIgnoreCase("no1Songs")) {
            request.setAttribute("songs", SongDAOImpl.getLongestNo1Songs(chartInfoId));
            request.setAttribute("currentNo1SongId", ReportService.getCurrentNo1SongId(chartInfoId));
            request.getRequestDispatcher("reports/no1-songs.jsp").forward(request, response);

        } else if (report.equalsIgnoreCase("topSongs")) {
            request.setAttribute("songs", SongDAOImpl.getBiggestScoreSongs(chartInfoId));
            request.setAttribute("currentlyChartingSongs", ReportService.getCurrentlyChartingMap(chartInfoId));
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
                request.setAttribute("songs", SongDAOImpl.getBiggestScoreSongsByDate(chartInfoId, sDate1, sDate2));
            }
            request.setAttribute("currentlyChartingSongs", ReportService.getCurrentlyChartingMap(chartInfoId));
            request.getRequestDispatcher("reports/top-songs-date.jsp").forward(request, response);

        } else if(report.equalsIgnoreCase("effectiveArtists")) {
            String sort = request.getParameter("sort");
            if (sort == null || sort.isEmpty()) {
                sort = "no1";
            }

            request.setAttribute("sort", sort);
            request.setAttribute("artists", ArtistSongService.getTopArtistsBySongs(chartInfoId, sort));
            request.getRequestDispatcher("reports/effective-artists.jsp").forward(request, response);
        } else if(report.equalsIgnoreCase("topArtists")) {
            request.setAttribute("artists", ArtistSongService.getTopArtists(chartInfoId));
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
                request.setAttribute("artists", ArtistSongService.getTopArtistsByDate(chartInfoId, sDate1, sDate2));
            }
            request.getRequestDispatcher("reports/top-artists-date.jsp").forward(request, response);

        } else if (report.equalsIgnoreCase("no1Debuts")) {
            request.setAttribute("positions", PositionDAOImpl.getNumberOneDebuts(chartInfoId));
            request.getRequestDispatcher("reports/no1-debuts.jsp").forward(request, response);

        } else if (report.equalsIgnoreCase("longestStallers")) {
            request.setAttribute("rows", ReportService.getLongestWaysToTop10(chartInfoId));
            request.setAttribute("currentlyChartingSongs", ReportService.getCurrentlyChartingMap(chartInfoId));
            request.getRequestDispatcher("reports/longest-stallers.jsp").forward(request, response);

        } else if (report.equalsIgnoreCase("longestSemihits")) {
            request.setAttribute("rows", ReportService.getLongestSemihits(chartInfoId));
            request.setAttribute("currentlyChartingSongs", ReportService.getCurrentlyChartingMap(chartInfoId));
            request.getRequestDispatcher("reports/longest-semihits.jsp").forward(request, response);

        } else if (report.equalsIgnoreCase("biggestLeaps")) {
            request.setAttribute("rows", PositionDAOImpl.getBiggestJumpsUp(chartInfoId));
            request.getRequestDispatcher("reports/biggest-leaps.jsp").forward(request, response);

        } else if (report.equalsIgnoreCase("biggestFalls")) {
            request.setAttribute("rows", PositionDAOImpl.getBiggestDrops(chartInfoId));
            request.getRequestDispatcher("reports/biggest-falls.jsp").forward(request, response);
        }
    }
}

package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.dto.ArtistSongRow;
import com.chart.TopChart.data.model.Song;
import com.chart.TopChart.service.ArtistSongService;
import com.chart.TopChart.web.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "artistsongs", value = "/artist")
public class ArtistSongsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        Integer chartInfoIdObj = SessionUtil.resolveChartInfoId(request);
        if (chartInfoIdObj == null) {
            response.sendRedirect("/");
            return;
        }
        int chartInfoId = chartInfoIdObj;

        String artist = request.getParameter("artist");
        List<ArtistSongRow> songRows = SongDAOImpl.getArtistSongRows(chartInfoId, artist);

        List<Song> songs = new ArrayList<>();
        for (ArtistSongRow row : songRows) {
            songs.add(row.getSong());
        }

        Integer lastIssueNumber = ChartDAOImpl.getLastIssueNumber(chartInfoId);

        for (ArtistSongRow row : songRows) {
            Song song = row.getSong();

            boolean currentlyCharting = false;

            if (lastIssueNumber != null && song != null) {
                currentlyCharting =
                        PositionDAOImpl.getPositionForSong(chartInfoId, song.getId(), lastIssueNumber) != null;
            }

            row.setCurrentlyCharting(currentlyCharting);
        }

        request.setAttribute("songRows", songRows);
        request.setAttribute("songs", songs);
        request.setAttribute("artist", artist);
        request.setAttribute("stats", ArtistSongService.getArtistTopStatsFromSongs(songs));

        request.getRequestDispatcher("artistsongs.jsp").forward(request, response);
        response.flushBuffer();
    }
}
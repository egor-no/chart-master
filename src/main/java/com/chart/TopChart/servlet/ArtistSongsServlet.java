package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Song;
import com.chart.TopChart.service.ArtistSongService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "artistsongs", value = "/artist")
public class ArtistSongsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String artist = request.getParameter("artist");
        List<Song> songs = SongDAOImpl.getByArtist(artist);

        request.setAttribute("songs", songs);
        request.setAttribute("artist", artist);
        request.setAttribute("stats", ArtistSongService.getArtistTopStats(artist));
        request.getRequestDispatcher("artistsongs.jsp").forward(request, response);
        response.flushBuffer();
    }
}
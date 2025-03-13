package com.chart.TopChart.servlet;


import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Song;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "songedit", value = "/songedit")
public class SongEditServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long idSong = Long.parseLong(request.getParameter("id"));

        request.setAttribute("song", SongDAOImpl.getById(idSong));
        request.setAttribute("search", request.getParameter("search"));
        request.getRequestDispatcher("songedit.jsp").forward(request, response);
        response.flushBuffer();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long idSong = Long.parseLong(request.getParameter("id"));
        String songName = request.getParameter("name");
        String artists = request.getParameter("artists");
        Song song = SongDAOImpl.getById(idSong);

        song.setName(songName);
        song.setArtists(artists);
        SongDAOImpl.update(song);

        response.sendRedirect("/songs?search=" + request.getParameter("search"));
    }
}
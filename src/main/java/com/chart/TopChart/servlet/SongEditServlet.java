package com.chart.TopChart.servlet;


import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.ChartInfo;
import com.chart.TopChart.data.model.Song;
import com.chart.TopChart.web.AuthUtil;

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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        long idSong;
        try {
            idSong = Long.parseLong(request.getParameter("id"));
        } catch (Exception e) {
            response.sendError(400, "Bad song id");
            return;
        }

        Song s;
        try {
            s = AuthUtil.requireOwnedSongViaPositions(request, idSong);
        } catch (AuthUtil.ForbiddenException ex) {
            AuthUtil.renderForbidden(request, response,
                    "You can't edit songs from someone else's chart.",
                    true);
            return;
        }

        request.setAttribute("song", s);
        request.setAttribute("search", request.getParameter("search"));
        request.setAttribute("artist", request.getParameter("artist"));
        request.getRequestDispatcher("songedit.jsp").forward(request, response);
        response.flushBuffer();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        long idSong;
        try {
            idSong = Long.parseLong(request.getParameter("id"));
        } catch (Exception e) {
            response.sendError(400, "Bad song id");
            return;
        }

        Song song;
        try {
            song = AuthUtil.requireOwnedSongViaPositions(request, idSong);
        } catch (AuthUtil.ForbiddenException ex) {
            response.sendError(403);
            return;
        }

        song.setName(request.getParameter("name"));
        song.setArtists(request.getParameter("artists"));
        SongDAOImpl.update(song);

        String search = request.getParameter("search");
        if (search == null) search = "";

        if (search.isEmpty()) {
            response.sendRedirect("/artist?artist=" + request.getParameter("artist"));
        } else {
            response.sendRedirect("/songs?search=" + search);
        }
    }
}
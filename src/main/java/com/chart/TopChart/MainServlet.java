package com.chart.TopChart;

import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Song;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "main", value = "/")
public class MainServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Song> songs = SongDAOImpl.getAll();

        request.setAttribute("songs", songs);
        request.getRequestDispatcher("main.jsp").forward(request, response);
        response.flushBuffer();
    }

    public void destroy() {
    }
}

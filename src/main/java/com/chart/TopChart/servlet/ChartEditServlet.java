package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.service.ChartService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "chartEdit", value = "/chartedit")
public class ChartEditServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long chartNumber = Long.parseLong(request.getParameter("chartNumber"));
        Chart chart = ChartDAOImpl.getById(chartNumber);

        if (chart.getId() != ChartDAOImpl.getLastId()) {
            response.sendRedirect("/?chartNumber=" + chart.getId());
        } else {
            String json = new Gson().toJson(SongDAOImpl.getAll());
            request.setAttribute("songs", json);
            request.setAttribute("chart", chart);
            request.getRequestDispatcher("chartedit.jsp").forward(request, response);
            response.flushBuffer();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long chartNumber = Long.parseLong(request.getParameter("chartNumber"));

        if (ChartService.deleteChart(chartNumber)) {
            System.out.println("****************** deleted charNumber " + chartNumber);
            String ids[] = request.getParameterValues("idSong[]");
            String artists[] = request.getParameterValues("artists[]");
            String name[] = request.getParameterValues("name[]");

            ChartService.formChart(ids, name, artists);
        } else {
            System.out.println("******************** not deleted charNumber " + chartNumber);
        }

        response.sendRedirect("/");
        response.flushBuffer();
    }
}

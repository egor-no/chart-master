package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.service.ChartService;
import com.chart.TopChart.web.AuthUtil;
import com.chart.TopChart.web.SessionUtil;
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
        Chart chart;
        try {
            chart = AuthUtil.requireOwnedChart(request, chartNumber);
        } catch (AuthUtil.ForbiddenException ex) {
            response.sendError(403);
            return;
        }

        int chartInfoId = SessionUtil.requireChartInfoId(request);
        Long lastId = ChartDAOImpl.getLastId(chartInfoId);
        if (lastId == null || chart.getId() != lastId) {
            response.sendRedirect("/?chartNumber=" + chart.getId());
            return;
        } else {
            String json = new Gson().toJson(SongDAOImpl.getAll(chartInfoId));
            request.setAttribute("songs", json);
            request.setAttribute("chart", chart);

            Long prevId = ChartDAOImpl.getPrevId(chartInfoId, chart.getId());
            Chart prev = (prevId == null) ? null : ChartDAOImpl.getById(chartInfoId, prevId);
            request.setAttribute("prevChart", prev == null ? null : ChartService.getChartFull(prev));
            request.getRequestDispatcher("chartedit.jsp").forward(request, response);
            response.flushBuffer();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long chartNumber = Long.parseLong(request.getParameter("chartNumber"));
        try {
            AuthUtil.requireOwnedChart(request, chartNumber);
        } catch (AuthUtil.ForbiddenException ex) {
            AuthUtil.renderForbidden(request, response,
                    "You can't edit someone else's chart.",
                    true);
            return;
        }
        int chartInfoId = SessionUtil.requireChartInfoId(request);
        if (ChartService.deleteChart(chartNumber, chartInfoId)) {
            String ids[] = request.getParameterValues("idSong[]");
            String artists[] = request.getParameterValues("artists[]");
            String name[] = request.getParameterValues("name[]");

            ChartService.formChart(chartInfoId, ids, name, artists);
        } else {
            System.out.println("******************** not deleted charNumber " + chartNumber);
        }

        response.sendRedirect("/chart");
        response.flushBuffer();
    }
}

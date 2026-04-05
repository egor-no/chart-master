package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.Position;
import com.chart.TopChart.data.model.Position_PK;
import com.chart.TopChart.data.model.Song;
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
import java.util.List;

@WebServlet(name = "chartAdd", value = "/chartadd")
public class ChartAddServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            AuthUtil.requireOwnedChartInfo(request);
        } catch (AuthUtil.ForbiddenException ex) {
            AuthUtil.renderForbidden(request, response,
                    "You can't add charts to someone else's chart.",
                    true);
            return;
        }

        int chartInfoId = SessionUtil.requireChartInfoId(request);

        String json = new Gson().toJson(SongDAOImpl.getAll(chartInfoId));
        request.setAttribute("songs", json);

        Integer lastIssueNumber = ChartDAOImpl.getLastIssueNumber(chartInfoId);
        Chart chart = (lastIssueNumber == null) ? null : ChartDAOImpl.getByIssueNumber(chartInfoId, lastIssueNumber);

        request.setAttribute("chart", chart == null ? null : ChartService.getChartFull(chart));
        request.getRequestDispatcher("chartadd.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        try {
            AuthUtil.requireOwnedChartInfo(request);
        } catch (AuthUtil.ForbiddenException ex) {
            response.sendError(403);
            return;
        }

        String ids[] = request.getParameterValues("idSong[]");
        String artists[] = request.getParameterValues("artists[]");
        String name[] = request.getParameterValues("name[]");

        int chartInfoId = SessionUtil.requireChartInfoId(request);
        ChartService.formChart(chartInfoId, ids, name, artists);

        response.sendRedirect("/chart");
        response.flushBuffer();
    }
}
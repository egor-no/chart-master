package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dto.ChartRun;
import com.chart.TopChart.data.dto.SongHistory;
import com.chart.TopChart.service.SongHistoryService;
import com.chart.TopChart.web.SessionUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "songhistory", value = "/songhistory")
public class SongHistoryServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long idSong = Long.parseLong(request.getParameter("idSong"));
        Integer currentIssueNumber;
        int chartInfoId = SessionUtil.requireChartInfoId(request);
        try {
            currentIssueNumber = Integer.parseInt(request.getParameter("chartNumber"));
        } catch (Exception ex) {
            currentIssueNumber = ChartDAOImpl.getLastIssueNumber(chartInfoId);
        }
        Boolean dateSearch = Boolean.parseBoolean(request.getParameter("dateSearch"));

        SongHistory songHistory;
        if (dateSearch) {
            String sDate1 = request.getParameter("date1");
            String sDate2 = request.getParameter("date2");
            songHistory  = SongHistoryService.getSongHistory(chartInfoId, idSong, sDate1, sDate2);
        } else {
            songHistory  = SongHistoryService.getSongHistory(chartInfoId, idSong, null, null);
        }
        songHistory.setCurrentIssue(currentIssueNumber);

        String json = new Gson().toJson(songHistory);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
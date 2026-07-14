package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.ChartInfoDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dto.ChartFull;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.service.ChartService;
import com.chart.TopChart.web.SessionUtil;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "chart", value = "/chart")
public class ChartServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer chartInfoId = SessionUtil.resolveChartInfoId(request);

        if (chartInfoId == null) {
            response.sendRedirect("/profile");
            return;
        }

        Chart chart = null;

        String chartDate = request.getParameter("date");
        if (chartDate != null && !chartDate.isEmpty()) {
            int issueNumber = ChartDAOImpl.getLastIssueNumberByDate(chartDate, chartInfoId);
            if (issueNumber > 0) {
                chart = ChartDAOImpl.getByIssueNumber(chartInfoId, issueNumber);
            }
        }

        if (chart == null) {
            String chartNumberStr = request.getParameter("chartNumber");
            if (chartNumberStr != null && !chartNumberStr.isEmpty()) {
                try {
                    Integer chartIssueNumber = Integer.parseInt(chartNumberStr);
                    chart = ChartDAOImpl.getByIssueNumber(chartInfoId, chartIssueNumber);
                } catch (Exception ignored) {
                }
            }
        }

        Integer lastIssueNumber = ChartDAOImpl.getLastIssueNumber(chartInfoId);
        if (chart == null) {
            if (lastIssueNumber != null) {
                chart = ChartDAOImpl.getByIssueNumber(chartInfoId, lastIssueNumber);
            }
        }

        if (chart == null) {
            Integer userId = SessionUtil.getUserId(request);
            boolean isOwner = userId != null && ChartInfoDAOImpl.isOwner(chartInfoId, userId);
            if (isOwner) {
                response.sendRedirect(request.getContextPath()
                        + "/chartadd?ci=" + chartInfoId + "&emptyChart=1");
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }

            return;
        }

        if (chart.getInfo() != null) {
            SessionUtil.saveChartContext(request, chart.getInfo());
        }

        boolean isLastChart = (lastIssueNumber != null && chart.getIssueNumber().equals(lastIssueNumber));

        ChartFull fullChart = ChartService.getChartFull(chart);
        request.setAttribute("chart", fullChart);
        request.setAttribute("isLastChart", isLastChart);

        int weeksAtNo1 = 0;
        if (fullChart.getPositions() != null && !fullChart.getPositions().isEmpty()) {
            long no1SongId = fullChart.getPositions().get(0).getPk().getSong().getId();
            weeksAtNo1 = PositionDAOImpl.getWeeksAtNumberOne(chartInfoId, no1SongId);
        }

        request.setAttribute("weeksAtNo1", weeksAtNo1);

        request.setAttribute("chartTitle", SessionUtil.getChartTitle(request));
        request.setAttribute("chartAuthorName", SessionUtil.getChartAuthorName(request));

        request.getRequestDispatcher("chart.jsp").forward(request, response);
    }
}
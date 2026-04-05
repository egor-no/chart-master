package com.chart.TopChart.web;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.ChartInfoDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.ChartInfo;
import com.chart.TopChart.data.model.Song;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class AuthUtil {
    private AuthUtil() {}

    public static ChartInfo requireOwnedChartInfo(HttpServletRequest req) {
        int userId = SessionUtil.requireUserId(req);
        int chartInfoId = SessionUtil.requireResolvedChartInfoId(req);

        ChartInfo ci = ChartInfoDAOImpl.getById(chartInfoId);
        if (ci == null || ci.getOwner() == null || ci.getOwner().getId() != userId) {
            throw new ForbiddenException("ChartInfo not owned by user");
        }
        return ci;
    }

    public static Chart requireOwnedChart(HttpServletRequest req, int chartIssueNumber) {
        ChartInfo ci = requireOwnedChartInfo(req);
        Chart c = ChartDAOImpl.getByIssueNumber(ci.getId(), chartIssueNumber);
        if (c == null || c.getInfo() == null || c.getInfo().getId() != ci.getId()) {
            throw new ForbiddenException("Chart not owned by user");
        }
        return c;
    }

    public static final class ForbiddenException extends RuntimeException {
        public ForbiddenException(String msg) { super(msg); }
    }

    public static void renderForbidden(HttpServletRequest request, HttpServletResponse response,
                                 String msg, boolean showProfileLink)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
        request.setAttribute("title", "Access denied");
        request.setAttribute("message", msg);
        request.setAttribute("showProfileLink", showProfileLink);

        request.getRequestDispatcher("/WEB-INF/jsp/forbidden.jsp")
                .forward(request, response);
    }

    public static Song requireOwnedSongViaPositions(HttpServletRequest req, long songId) {
        ChartInfo ci = requireOwnedChartInfo(req);

        if (!SongDAOImpl.existsInChartInfo(songId, ci.getId())) {
            throw new ForbiddenException("Song not owned by user");
        }

        Song s = SongDAOImpl.getById(songId);
        if (s == null) {
            throw new ForbiddenException("Song not found");
        }

        return s;
    }
}
package com.chart.TopChart.web;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.ChartInfoDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.ChartInfo;

import javax.servlet.http.HttpServletRequest;

public final class AuthUtil {
    private AuthUtil() {}

    public static ChartInfo requireOwnedChartInfo(HttpServletRequest req) {
        int userId = SessionUtil.requireUserId(req);
        int chartInfoId = SessionUtil.requireChartInfoId(req);

        ChartInfo ci = ChartInfoDAOImpl.getById(chartInfoId);
        if (ci == null || ci.getOwner() == null || ci.getOwner().getId() != userId) {
            throw new ForbiddenException("ChartInfo not owned by user");
        }
        return ci;
    }

    public static Chart requireOwnedChart(HttpServletRequest req, long chartId) {
        ChartInfo ci = requireOwnedChartInfo(req);
        Chart c = ChartDAOImpl.getById(ci.getId(), chartId);
        if (c == null || c.getInfo() == null || c.getInfo().getId() != ci.getId()) {
            throw new ForbiddenException("Chart not owned by user");
        }
        return c;
    }

    public static final class ForbiddenException extends RuntimeException {
        public ForbiddenException(String msg) { super(msg); }
    }
}
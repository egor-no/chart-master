package com.chart.TopChart.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class SessionUtil {
    private SessionUtil() {}

    public static Integer getUserId(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        Object v = (s == null) ? null : s.getAttribute(SessionKeys.USER_ID);
        return (v instanceof Integer) ? (Integer) v : null;
    }

    public static Integer getChartInfoId(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        Object v = (s == null) ? null : s.getAttribute(SessionKeys.CHART_INFO_ID);
        return (v instanceof Integer) ? (Integer) v : null;
    }

    public static int requireUserId(HttpServletRequest req) {
        Integer id = getUserId(req);
        if (id == null) throw new IllegalStateException("Not authenticated");
        return id;
    }

    public static int requireChartInfoId(HttpServletRequest req) {
        Integer id = getChartInfoId(req);
        if (id == null) throw new IllegalStateException("Chart not selected");
        return id;
    }
}
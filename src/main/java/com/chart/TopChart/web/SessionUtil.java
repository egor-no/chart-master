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

    public static int requireResolvedChartInfoId(HttpServletRequest req) {
        Integer id = resolveChartInfoId(req);
        if (id == null) throw new IllegalStateException("Chart not selected");
        return id;
    }

    public static Integer resolveChartInfoId(HttpServletRequest req) {
        Integer fromParam = null;

        String ciStr = req.getParameter("ci");
        if (ciStr != null && !ciStr.isEmpty()) {
            try { fromParam = Integer.parseInt(ciStr); } catch (Exception ignored) {}
        }

        if (fromParam != null) {
            HttpSession s = req.getSession(true);
            s.setAttribute(SessionKeys.CHART_INFO_ID, fromParam);

            s.removeAttribute(SessionKeys.OWNER_CI_ID);
            s.removeAttribute(SessionKeys.OWNER_FLAG);

            return fromParam;
        }

        return getChartInfoId(req);
    }
}
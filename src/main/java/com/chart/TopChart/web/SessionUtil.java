package com.chart.TopChart.web;

import com.chart.TopChart.data.dao.ChartInfoDAOImpl;
import com.chart.TopChart.data.model.ChartInfo;

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
            s.removeAttribute(SessionKeys.CHART_TITLE);
            s.removeAttribute(SessionKeys.CHART_AUTHOR_NAME);

            return fromParam;
        }

        return getChartInfoId(req);
    }

    public static void saveChartContext(
            HttpServletRequest req,
            ChartInfo chartInfo
    ) {
        HttpSession session = req.getSession(true);

        if (chartInfo == null) {
            session.removeAttribute(SessionKeys.CHART_INFO_ID);
            session.removeAttribute(SessionKeys.CHART_TITLE);
            session.removeAttribute(SessionKeys.CHART_AUTHOR_NAME);
            return;
        }

        session.setAttribute(
                SessionKeys.CHART_INFO_ID,
                chartInfo.getId()
        );

        String chartTitle = chartInfo.getTitle();

        if (chartTitle == null || chartTitle.trim().isEmpty()) {
            chartTitle = "TOP40";
        }

        String chartAuthorName = "somebody";

        if (chartInfo.getOwner() != null) {
            String nickname = chartInfo.getOwner().getNickname();

            if (nickname != null && !nickname.trim().isEmpty()) {
                chartAuthorName = nickname;
            } else {
                String login = chartInfo.getOwner().getLogin();

                if (login != null && !login.trim().isEmpty()) {
                    chartAuthorName = login;
                }
            }
        }

        session.setAttribute(
                SessionKeys.CHART_TITLE,
                chartTitle
        );

        session.setAttribute(
                SessionKeys.CHART_AUTHOR_NAME,
                chartAuthorName
        );
    }

    public static String getChartTitle(HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        Object value = session == null
                ? null
                : session.getAttribute(SessionKeys.CHART_TITLE);

        return value instanceof String
                ? (String) value
                : "TOP40";
    }

    public static String getChartAuthorName(HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        Object value = session == null
                ? null
                : session.getAttribute(SessionKeys.CHART_AUTHOR_NAME);

        return value instanceof String
                ? (String) value
                : "somebody";
    }

    public static void ensureChartContext(HttpServletRequest request, int chartInfoId) {
        HttpSession session = request.getSession(true);
        Object currentTitle = session.getAttribute(SessionKeys.CHART_TITLE);
        Object currentAuthor = session.getAttribute(SessionKeys.CHART_AUTHOR_NAME);

        if (currentTitle instanceof String && currentAuthor instanceof String) {
            return;
        }

        ChartInfo chartInfo = ChartInfoDAOImpl.getById(chartInfoId);
        if (chartInfo == null) {
            return;
        }

        saveChartContext(request, chartInfo);
    }
}
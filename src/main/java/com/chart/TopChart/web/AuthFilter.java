package com.chart.TopChart.web;

import com.chart.TopChart.data.dao.ChartInfoDAOImpl;
import com.chart.TopChart.data.model.ChartInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    public static final String IS_OWNER = "isOwner";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();

        HttpSession s = request.getSession(false);

        Integer userId = null;
        Integer chartInfoId = null;

        if (s != null) {
            Object u = s.getAttribute(SessionKeys.USER_ID);
            Object ci = s.getAttribute(SessionKeys.CHART_INFO_ID);

            if (u instanceof Integer) userId = (Integer) u;
            if (ci instanceof Integer) chartInfoId = (Integer) ci;
        }

        boolean loggedInFlag = (userId != null);
        boolean isOwnerFlag = false;

        if (loggedInFlag && chartInfoId != null && s != null) {
            Object cachedCi = s.getAttribute(SessionKeys.OWNER_CI_ID);
            Object cachedFlag = s.getAttribute(SessionKeys.OWNER_FLAG);

            if (cachedCi instanceof Integer && cachedFlag instanceof Boolean
                    && ((Integer) cachedCi).intValue() == chartInfoId.intValue()) {

                isOwnerFlag = (Boolean) cachedFlag;

            } else {
                ChartInfo ci = ChartInfoDAOImpl.getById(chartInfoId);
                if (ci != null && ci.getOwner() != null) {
                    isOwnerFlag = (ci.getOwner().getId() == userId);
                }

                s.setAttribute(SessionKeys.OWNER_CI_ID, chartInfoId);
                s.setAttribute(SessionKeys.OWNER_FLAG, isOwnerFlag);
            }
        }

        request.setAttribute("loggedIn", loggedInFlag);
        request.setAttribute("isOwner", isOwnerFlag);

        if (!isPublic(uri) && !loggedInFlag) {
            response.sendRedirect("/login");
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean isPublic(String uri) {
        if (uri == null) return true;

        if (uri.equals("/") || uri.equals("/login")) return true;

        if (uri.equals("/chart")) return true;
        if (uri.equals("/reports")) return true;
        if (uri.equals("/artists")) return true;
        if (uri.equals("/songs")) return true;

        if (uri.equals("/artist")) return true;
        if (uri.equals("/song")) return true;
        if (uri.equals("/songhistory")) return true;

        if (uri.startsWith("/css/")) return true;
        if (uri.startsWith("/icons/")) return true;
        if (uri.startsWith("/js/")) return true;
        if (uri.startsWith("/components/")) return true;

        return false;
    }
}
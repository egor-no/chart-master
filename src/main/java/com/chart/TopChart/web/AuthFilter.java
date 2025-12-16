package com.chart.TopChart.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();

        if (isPublic(uri)) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute(SessionKeys.USER_ID) != null;

        if (!loggedIn) {
            response.sendRedirect("/login");
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean isPublic(String uri) {
        if (uri == null) return true;

        if (uri.equals("/") || uri.equals("/login")) return true;

        if (uri.startsWith("/css/")) return true;
        if (uri.startsWith("/icons/")) return true;
        if (uri.startsWith("/js/")) return true;
        if (uri.startsWith("/components/")) return true;

        return false;
    }
}
package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.service.ChartService;
import com.chart.TopChart.web.SessionKeys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "chartarchive", value = "/chartarchive")
public class ChartArchiveServlet extends HttpServlet {

    private static final int PAGE_SIZE = 20;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer chartInfoId = null;

        String ciStr = request.getParameter("ci");
        if (ciStr != null && !ciStr.isEmpty()) {
            try {
                chartInfoId = Integer.parseInt(ciStr);
            } catch (Exception ignored) {}
        }

        if (chartInfoId == null) {
            HttpSession session = request.getSession(false);
            Object v = session == null ? null : session.getAttribute(SessionKeys.CHART_INFO_ID);
            if (v instanceof Integer) chartInfoId = (Integer) v;
        }

        if (chartInfoId == null) {
            response.sendRedirect("/profile");
            return;
        }

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Math.max(1, Integer.parseInt(pageStr));
            } catch (Exception ignored) {}
        }

        long total = ChartDAOImpl.getChartsCount(chartInfoId);
        int totalPages = (int) Math.ceil(total / (double) PAGE_SIZE);

        if (totalPages > 0 && page > totalPages) {
            page = totalPages;
        }

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pages.add(i);
        }

        request.setAttribute("pages", pages);

        request.setAttribute("rows", ChartService.getArchiveRows(chartInfoId, page, PAGE_SIZE));
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("chartInfoId", chartInfoId);

        request.getRequestDispatcher("chart-archive.jsp").forward(request, response);
    }
}
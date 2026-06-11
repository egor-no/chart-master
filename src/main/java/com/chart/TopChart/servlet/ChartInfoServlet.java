package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartInfoDAOImpl;
import com.chart.TopChart.data.model.ChartInfo;
import com.chart.TopChart.data.model.User;
import com.chart.TopChart.web.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "chartInfo", value = "/chartInfo")
public class ChartInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            request.getRequestDispatcher("chartInfoEdit.jsp").forward(request, response);
            return;
        }

        if ("edit".equals(action)) {
            showChartEditForm(request, response);
            return;
        }

        response.sendRedirect("/profile");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createChartInfo(request, response);
            return;
        }

        if ("update".equals(action)) {
            updateChartInfo(request, response);
            return;
        }

        if ("delete".equals(action)) {
            deleteChartInfo(request, response);
            return;
        }

        response.sendRedirect("/profile");
    }

    private static void showChartEditForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Integer viewerId = SessionUtil.getUserId(request);

        String ciParam = request.getParameter("ci");
        if (ciParam == null || ciParam.trim().isEmpty()) {
            response.sendRedirect("/profile");
            return;
        }

        int ciId = Integer.parseInt(ciParam);
        ChartInfo chartInfo = ChartInfoDAOImpl.getById(ciId);

        if (chartInfo == null || chartInfo.getOwner() == null || chartInfo.getOwner().getId() != viewerId) {
            response.sendError(403);
            return;
        }

        request.setAttribute("chartInfo", chartInfo);
        request.getRequestDispatcher("chartInfoEdit.jsp").forward(request, response);
    }

    private static void createChartInfo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer viewerId = SessionUtil.getUserId(request);
        if (viewerId == null) {
            response.sendRedirect("/login");
            return;
        }

        User owner = new User();
        owner.setId(viewerId);

        ChartInfo ci = new ChartInfo();
        ci.setOwner(owner);
        ci.setTitle(request.getParameter("title"));
        ci.setSize(Integer.parseInt(request.getParameter("size")));
        ci.setDescription(request.getParameter("description"));
        ci.setFieldsAmount(parseIntegerOrNull(request.getParameter("fieldsAmount")));
        ci.setField1Name(request.getParameter("field1Name"));
        ci.setField2Name(request.getParameter("field2Name"));
        ci.setCreatedAt(java.time.LocalDateTime.now());

        ChartInfoDAOImpl.save(ci);

        response.sendRedirect("/profile");
    }

    private static void updateChartInfo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer viewerId = SessionUtil.getUserId(request);
        if (viewerId == null) {
            response.sendRedirect("/login");
            return;
        }

        int ciId = Integer.parseInt(request.getParameter("ci"));
        ChartInfo ci = ChartInfoDAOImpl.getById(ciId);

        if (ci == null || ci.getOwner().getId() != viewerId) {
            response.sendError(403);
            return;
        }

        ci.setTitle(request.getParameter("title"));
        ci.setSize(Integer.parseInt(request.getParameter("size")));
        ci.setDescription(request.getParameter("description"));
        ci.setFieldsAmount(parseIntegerOrNull(request.getParameter("fieldsAmount")));
        ci.setField1Name(request.getParameter("field1Name"));
        ci.setField2Name(request.getParameter("field2Name"));

        ChartInfoDAOImpl.update(ci);

        response.sendRedirect("/profile");
    }

    private static void deleteChartInfo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Integer viewerId = SessionUtil.getUserId(request);
        if (viewerId == null) {
            response.sendRedirect("/login");
            return;
        }

        String ciParam = request.getParameter("ci");
        if (ciParam == null || ciParam.trim().isEmpty()) {
            response.sendRedirect("/profile");
            return;
        }

        int ciId = Integer.parseInt(ciParam);

        ChartInfoDAOImpl.deleteWithUnusedSongs(ciId, viewerId);

        response.sendRedirect("/profile");
    }

    private static Integer parseIntegerOrNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(value.trim());
    }
}

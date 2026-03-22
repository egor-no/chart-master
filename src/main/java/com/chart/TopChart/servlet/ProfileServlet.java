package com.chart.TopChart.servlet;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.UserDAOImpl;
import com.chart.TopChart.data.dto.ChartBasic;
import com.chart.TopChart.data.model.ChartInfo;
import com.chart.TopChart.data.model.User;
import com.chart.TopChart.web.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "profile", value = "/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            showEditPage(request, response);
            return;
        }

        showProfilePage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if ("update".equals(action)) {
            updateProfile(request, response);
            return;
        }

        response.sendRedirect("/profile");
    }

    private void showProfilePage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Integer viewerId = SessionUtil.getUserId(request);

        Integer userIdToShow = null;
        String uStr = request.getParameter("u");
        if (uStr != null && !uStr.isEmpty()) {
            try {
                userIdToShow = Integer.parseInt(uStr);
            } catch (Exception ignored) {}
        }

        if (userIdToShow == null) {
            if (viewerId != null) {
                userIdToShow = viewerId;
            } else {
                response.sendRedirect("/login");
                return;
            }
        }

        User profileUser = UserDAOImpl.getById(userIdToShow);
        if (profileUser == null) {
            response.sendError(404, "User not found");
            return;
        }

        boolean isMine = (viewerId != null && viewerId.intValue() == profileUser.getId());

        List<ChartInfo> charts = profileUser.getCharts();

        List<Integer> ciIds = new ArrayList<>();
        if (charts != null) {
            for (ChartInfo ci : charts) {
                ciIds.add(ci.getId());
            }
        }

        Map<Integer, List<ChartBasic>> lastMap =
                ChartDAOImpl.getLastChartsByChartInfoIds(ciIds, 4);

        List<ProfileChartCard> cards = new ArrayList<>();
        if (charts != null) {
            for (ChartInfo ci : charts) {
                cards.add(new ProfileChartCard(ci, lastMap.get(ci.getId())));
            }
        }

        request.setAttribute("profileUser", profileUser);
        request.setAttribute("isMine", isMine);
        request.setAttribute("cards", cards);

        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    private void showEditPage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Integer viewerId = SessionUtil.getUserId(request);
        if (viewerId == null) {
            response.sendRedirect("/login");
            return;
        }

        User profileUser = UserDAOImpl.getById(viewerId);
        if (profileUser == null) {
            response.sendError(404, "User not found");
            return;
        }

        request.setAttribute("profileUser", profileUser);
        request.getRequestDispatcher("profileEdit.jsp").forward(request, response);
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Integer viewerId = SessionUtil.getUserId(request);
        if (viewerId == null) {
            response.sendRedirect("/login");
            return;
        }

        User user = UserDAOImpl.getById(viewerId);
        if (user == null) {
            response.sendError(404, "User not found");
            return;
        }

        String nickname = trim(request.getParameter("nickname"));
        String slogan = trim(request.getParameter("slogan"));
        String bio = trim(request.getParameter("bio"));
        String avatar = trim(request.getParameter("avatar"));
        String password = trim(request.getParameter("password"));

        boolean hasError = false;

        if (isBlank(nickname)) {
            request.setAttribute("nicknameError", "Nickname is required.");
            hasError = true;
        }

        if (hasError) {
            user.setNickname(nickname);
            user.setSlogan(slogan);
            user.setBio(bio);
            user.setAvatar(avatar);

            request.setAttribute("profileUser", user);
            request.getRequestDispatcher("profileEdit.jsp").forward(request, response);
            return;
        }

        user.setNickname(nickname);
        user.setSlogan(slogan);
        user.setBio(bio);
        user.setAvatar(avatar);

        if (!isBlank(password)) {
            user.setPassword(password);
        }

        UserDAOImpl.update(user);

        response.sendRedirect("/profile");
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static class ProfileChartCard {
        private final ChartInfo ci;
        private final List<ChartBasic> lastIssues;

        public ProfileChartCard(ChartInfo ci, List<ChartBasic> lastIssues) {
            this.ci = ci;
            this.lastIssues = lastIssues;
        }

        public ChartInfo getCi() {
            return ci;
        }

        public List<ChartBasic> getLastIssues() {
            return lastIssues;
        }
    }
}
package com.chart.TopChart.servlet;

import com.chart.TopChart.data.model.User;
import com.chart.TopChart.service.profile.*;
import com.chart.TopChart.web.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(name = "profile", value = "/profile")
@MultipartConfig
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
            Integer viewerId = SessionUtil.getUserId(request);
            if (viewerId == null) {
                response.sendRedirect("/login");
                return;
            }

            ProfileUpdateCommand cmd = new ProfileUpdateCommand();
            cmd.setViewerId(viewerId);
            cmd.setNickname(request.getParameter("nickname"));
            cmd.setSlogan(request.getParameter("slogan"));
            cmd.setBio(request.getParameter("bio"));
            cmd.setPassword(request.getParameter("password"));
            cmd.setRemoveAvatar("1".equals(request.getParameter("removeAvatar")));
            cmd.setAvatarPart(request.getPart("avatarFile"));
            cmd.setUploadPath(request.getServletContext().getRealPath("/avatars"));

            ProfileUpdateResult result = ProfileService.updateProfile(cmd);

            if (!result.isSuccess()) {
                if (result.getNicknameError() != null) {
                    request.setAttribute("nicknameError", result.getNicknameError());
                }
                request.setAttribute("profileUser", result.getProfileUser());
                request.getRequestDispatcher("profileEdit.jsp").forward(request, response);
                return;
            }

            response.sendRedirect("/profile");
            return;
        }

        if ("deactivate".equals(action)) {
            Integer viewerId = SessionUtil.getUserId(request);

            if (ProfileService.deactivateProfile(viewerId)) {
                response.sendRedirect("/deactivated");
            } else {
                response.sendRedirect("/login");
            }
            return;
        }

        if ("activate".equals(action)) {
            Integer viewerId = SessionUtil.getUserId(request);

            if (ProfileService.activateProfile(viewerId)) {
                response.sendRedirect("/profile?activated=1");
            } else {
                response.sendRedirect("/login");
            }
            return;
        }

        if ("delete".equals(action)) {
            Integer viewerId = SessionUtil.getUserId(request);
            String uploadPath = request.getServletContext().getRealPath("/avatars");
            if (ProfileService.deleteProfile(viewerId, uploadPath)) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }

                response.sendRedirect("/?deleted=1");
                return;
            } else {
                response.sendRedirect("/login");
            }
        }

        response.sendRedirect("/profile");
    }

    private static void showEditPage(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        Integer viewerId = SessionUtil.getUserId(request);
        if (viewerId == null) {
            response.sendRedirect("/login");
            return;
        }

        User profileUser = ProfileService.getEditableProfile(viewerId);
        if (profileUser == null) {
            response.sendError(404, "User not found");
            return;
        }

        request.setAttribute("profileUser", profileUser);
        request.getRequestDispatcher("profileEdit.jsp").forward(request, response);
    }

    private static void showProfilePage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Integer viewerId = SessionUtil.getUserId(request);
        String uStr = request.getParameter("u");

        ProfilePageData data = ProfileService.getProfilePageData(viewerId, uStr);

        if (data == null) {
            response.sendError(404, "User not found");
            return;
        }

        if (data.isRedirectToLogin()) {
            response.sendRedirect("/login");
            return;
        }

        request.setAttribute("deactivatedFormatted", data.getDeactivatedFormatted());
        request.setAttribute("profileUser", data.getProfileUser());
        request.setAttribute("isMine", data.isMine());
        request.setAttribute("cards", data.getCards());

        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}
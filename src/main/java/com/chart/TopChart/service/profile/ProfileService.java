package com.chart.TopChart.service.profile;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.UserDAOImpl;
import com.chart.TopChart.data.dto.ChartBasic;
import com.chart.TopChart.data.model.ChartInfo;
import com.chart.TopChart.data.model.User;
import com.chart.TopChart.web.SessionUtil;
import util.AvatarUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileService {

    public static ProfileUpdateResult updateProfile(ProfileUpdateCommand cmd)
            throws IOException {

        if (cmd.getViewerId() == null) {
            return new ProfileUpdateResult(false, null, null);
        }

        User user = UserDAOImpl.getById(cmd.getViewerId());
        if (user == null) {
            return new ProfileUpdateResult(false, null, null);
        }

        String nickname = trim(cmd.getNickname());
        String slogan = trim(cmd.getSlogan());
        String bio = trim(cmd.getBio());
        String password = trim(cmd.getPassword());

        if (isBlank(nickname)) {
            user.setNickname(nickname);
            user.setSlogan(slogan);
            user.setBio(bio);
            return new ProfileUpdateResult(false, "Nickname is required.", user);
        }

        String uploadPath = cmd.getUploadPath();
        String oldAvatar = user.getAvatar();

        Part avatarPart = cmd.getAvatarPart();
        boolean hasNewAvatar = avatarPart != null && avatarPart.getSize() > 0;

        if (hasNewAvatar) {
            String ext = AvatarUtil.getFileExtension(avatarPart);

            if (ext != null && AvatarUtil.isAvatarFileAllowed(ext)) {
                String newFileName = AvatarUtil.getNextAvatarFileName(uploadPath, ext);
                avatarPart.write(uploadPath + File.separator + newFileName);
                user.setAvatar(newFileName);

                if (oldAvatar != null && !oldAvatar.trim().isEmpty()) {
                    AvatarUtil.deleteAvatarFile(uploadPath, oldAvatar);
                }
            }
        } else if (cmd.isRemoveAvatar()) {
            if (oldAvatar != null && !oldAvatar.trim().isEmpty()) {
                AvatarUtil.deleteAvatarFile(uploadPath, oldAvatar);
            }
            user.setAvatar(null);
        }

        user.setNickname(nickname);
        user.setSlogan(slogan);
        user.setBio(bio);

        if (!isBlank(password)) {
            user.setPassword(password);
        }

        UserDAOImpl.update(user);

        return new ProfileUpdateResult(true, null, user);
    }

    public static boolean deactivateProfile(Integer viewerId) {
        if (viewerId == null) {
            return false;
        }

        User user = UserDAOImpl.getById(viewerId);
        if (user == null) {
            return false;
        }

        user.setActive(false);
        user.setDeactivatedAt(java.time.LocalDateTime.now());
        UserDAOImpl.update(user);
        return true;
    }

    public static boolean deleteProfile(Integer viewerId, String uploadPath) {
        if (viewerId == null) {
            return false;
        }

        User user = UserDAOImpl.getById(viewerId);
        if (user == null) {
            return false;
        }

        if (user.getAvatar() != null && !user.getAvatar().trim().isEmpty()) {
            AvatarUtil.deleteAvatarFile(uploadPath, user.getAvatar());
        }

        UserDAOImpl.delete(viewerId);
        return true;
    }

    public static boolean activateProfile(Integer viewerId) {
        if (viewerId == null) {
            return false;
        }

        User user = UserDAOImpl.getById(viewerId);
        if (user == null) {
            return false;
        }

        user.setActive(true);
        user.setDeactivatedAt(null);
        UserDAOImpl.update(user);
        return true;
    }

    public static User getEditableProfile(Integer viewerId) {
        if (viewerId == null) {
            return null;
        }

        return UserDAOImpl.getById(viewerId);
    }

    public static ProfilePageData getProfilePageData(Integer viewerId, String uStr) {
        Integer userIdToShow = null;

        if (uStr != null && !uStr.isEmpty()) {
            try {
                userIdToShow = Integer.parseInt(uStr);
            } catch (Exception ignored) {}
        }

        if (userIdToShow == null) {
            if (viewerId != null) {
                userIdToShow = viewerId;
            } else {
                return new ProfilePageData(null, false, null, null, true);
            }
        }

        User profileUser = UserDAOImpl.getById(userIdToShow);
        if (profileUser == null) {
            return null;
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

        String deactivatedFormatted = null;
        if (profileUser.getDeactivatedAt() != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            deactivatedFormatted = profileUser.getDeactivatedAt().format(fmt);
        }

        return new ProfilePageData(profileUser, isMine, cards, deactivatedFormatted, false);
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

}

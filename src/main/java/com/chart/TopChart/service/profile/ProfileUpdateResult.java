package com.chart.TopChart.service.profile;

import com.chart.TopChart.data.model.User;

public class ProfileUpdateResult {
    private final boolean success;
    private final String nicknameError;
    private final User profileUser;

    public ProfileUpdateResult(boolean success, String nicknameError, User profileUser) {
        this.success = success;
        this.nicknameError = nicknameError;
        this.profileUser = profileUser;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getNicknameError() {
        return nicknameError;
    }

    public User getProfileUser() {
        return profileUser;
    }
}
package com.chart.TopChart.service.profile;

import com.chart.TopChart.data.model.User;

import java.util.List;

public class ProfilePageData {
    private final User profileUser;
    private final boolean isMine;
    private final List<ProfileChartCard> cards;
    private final String deactivatedFormatted;
    private final boolean redirectToLogin;

    public ProfilePageData(User profileUser, boolean isMine,
                           List<ProfileChartCard> cards,
                           String deactivatedFormatted,
                           boolean redirectToLogin) {
        this.profileUser = profileUser;
        this.isMine = isMine;
        this.cards = cards;
        this.deactivatedFormatted = deactivatedFormatted;
        this.redirectToLogin = redirectToLogin;
    }

    public User getProfileUser() {
        return profileUser;
    }

    public boolean isMine() {
        return isMine;
    }

    public List<ProfileChartCard> getCards() {
        return cards;
    }

    public String getDeactivatedFormatted() {
        return deactivatedFormatted;
    }

    public boolean isRedirectToLogin() {
        return redirectToLogin;
    }
}
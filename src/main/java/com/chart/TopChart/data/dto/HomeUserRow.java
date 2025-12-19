package com.chart.TopChart.data.dto;

public class HomeUserRow {
    private int id;
    private String login;
    private String nickname;
    private long chartsCount;

    public HomeUserRow(int id, String login, String nickname, long chartsCount) {
        this.id = id;
        this.login = login;
        this.nickname = nickname;
        this.chartsCount = chartsCount;
    }

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getNickname() { return nickname; }
    public long getChartsCount() { return chartsCount; }
}

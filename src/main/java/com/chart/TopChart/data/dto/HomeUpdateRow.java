package com.chart.TopChart.data.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomeUpdateRow {

    public enum Type { USER, CHART_INFO, CHART_ISSUE }

    private final Type type;
    private final LocalDateTime sortTime;

    private final Integer userId;
    private final String userNickname;

    private final Integer chartInfoId;
    private final String chartInfoTitle;

    private final Long chartId;
    private final String chartDate;     // YYYY-MM-DD
    private final String ownerNickname;
    private final String createdAtStr;

    private HomeUpdateRow(Type type, LocalDateTime sortTime,
                          Integer userId, String userNickname,
                          Integer chartInfoId, String chartInfoTitle,
                          Long chartId, String chartDate,
                          String ownerNickname,
                          String createdAtStr) {

        this.type = type;
        this.sortTime = sortTime;
        this.userId = userId;
        this.userNickname = userNickname;
        this.chartInfoId = chartInfoId;
        this.chartInfoTitle = chartInfoTitle;
        this.chartId = chartId;
        this.chartDate = chartDate;
        this.ownerNickname = ownerNickname;
        this.createdAtStr = createdAtStr;
    }

    private static final DateTimeFormatter DT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static HomeUpdateRow user(int userId, String nickname, LocalDateTime createdAt) {
        String s = (createdAt == null) ? "" : createdAt.format(DT);
        return new HomeUpdateRow(Type.USER, createdAt,
                userId, nickname, null, null, null, null, null, s);
    }

    public static HomeUpdateRow chartInfo(int ciId, String title, String ownerNickname, LocalDateTime createdAt) {
        String s = (createdAt == null) ? "" : createdAt.format(DT);
        return new HomeUpdateRow(Type.CHART_INFO, createdAt,
                null, null, ciId, title, null, null, ownerNickname, s);
    }

    public static HomeUpdateRow issue(int ciId, String ciTitle, String ownerNickname,
                                      long chartId, String chartDate, LocalDateTime sortTime) {
        return new HomeUpdateRow(Type.CHART_ISSUE, sortTime,
                null, null, ciId, ciTitle, chartId, chartDate, ownerNickname, null);
    }

    public Type getType() { return type; }
    public LocalDateTime getSortTime() { return sortTime; }

    public Integer getUserId() { return userId; }
    public String getUserNickname() { return userNickname; }

    public Integer getChartInfoId() { return chartInfoId; }
    public String getChartInfoTitle() { return chartInfoTitle; }

    public Long getChartId() { return chartId; }
    public String getChartDate() { return chartDate; }

    public String getOwnerNickname() { return ownerNickname; }
    public String getCreatedAtStr() { return createdAtStr; }

}
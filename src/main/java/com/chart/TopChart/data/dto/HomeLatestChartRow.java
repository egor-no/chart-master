package com.chart.TopChart.data.dto;

public class HomeLatestChartRow {
    private int chartInfoId;
    private String chartInfoTitle;
    private long chartId;
    private String date;
    private String ownerNickname;

    public HomeLatestChartRow(int chartInfoId, String chartInfoTitle, long chartId, String date, String ownerNickname) {
        this.chartInfoId = chartInfoId;
        this.chartInfoTitle = chartInfoTitle;
        this.chartId = chartId;
        this.date = date;
        this.ownerNickname = ownerNickname;
    }

    public int getChartInfoId() { return chartInfoId; }
    public String getChartInfoTitle() { return chartInfoTitle; }
    public long getChartId() { return chartId; }
    public String getDate() { return date; }
    public String getOwnerNickname() { return ownerNickname; }
}
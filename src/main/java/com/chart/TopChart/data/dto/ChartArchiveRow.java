package com.chart.TopChart.data.dto;

public class ChartArchiveRow {
    private int issueNumber;
    private String date;
    private String top1;
    private String top2;
    private String top3;

    public ChartArchiveRow(int issueNumber, String date, String top1, String top2, String top3) {
        this.issueNumber = issueNumber;
        this.date = date;
        this.top1 = top1;
        this.top2 = top2;
        this.top3 = top3;
    }

    public int getIssueNumber() { return issueNumber; }
    public String getDate() { return date; }
    public String getTop1() { return top1; }
    public String getTop2() { return top2; }
    public String getTop3() { return top3; }
}
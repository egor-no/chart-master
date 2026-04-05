package com.chart.TopChart.data.dto;

import javax.persistence.Column;

public class ChartBasic {

    private int issueNumber;
    private String date;

    public ChartBasic() {
    }

    public ChartBasic(int issueNumber, String date) {
        this.issueNumber = issueNumber;
        this.date = date;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

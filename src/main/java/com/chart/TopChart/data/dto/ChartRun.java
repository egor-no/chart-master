package com.chart.TopChart.data.dto;

import java.util.List;

public class ChartRun {

    private List<ChartRunPosition> positions;
    private ChartBasic firstChart;
    private ChartBasic lastChart;

    public ChartRun() {
    }

    public ChartRun(List<ChartRunPosition> positions, ChartBasic firstChart, ChartBasic lastChart) {
        this.positions = positions;
        this.firstChart = firstChart;
        this.lastChart = lastChart;
    }

    public List<ChartRunPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<ChartRunPosition> positions) {
        this.positions = positions;
    }

    public ChartBasic getFirstChart() {
        return firstChart;
    }

    public void setFirstChart(ChartBasic firstChart) {
        this.firstChart = firstChart;
    }

    public ChartBasic getLastChart() {
        return lastChart;
    }

    public void setLastChart(ChartBasic lastChart) {
        this.lastChart = lastChart;
    }

    public static class ChartRunPosition {
        private Integer position;
        private Integer issueNumber;
        private String date;

        public ChartRunPosition() {
        }

        public ChartRunPosition(Integer position, Integer issueNumber, String date) {
            this.position = position;
            this.issueNumber = issueNumber;
            this.date = date;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public Integer getIssueNumber() {
            return issueNumber;
        }

        public void setIssueNumber(Integer issueNumber) {
            this.issueNumber = issueNumber;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
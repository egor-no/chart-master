package com.chart.TopChart.data.dto;

import java.util.List;

public class SongHistory {

    private Integer peak;
    private Integer currentIssue;
    private List<ChartRun> chartRuns;

    public SongHistory() {
    }

    public SongHistory(Integer peak, Integer currentIssue, List<ChartRun> chartRuns) {
        this.peak = peak;
        this.currentIssue = currentIssue;
        this.chartRuns = chartRuns;
    }

    public Integer getPeak() {
        return peak;
    }

    public void setPeak(Integer peak) {
        this.peak = peak;
    }

    public Integer getCurrentIssue() {
        return currentIssue;
    }

    public void setCurrentIssue(Integer currentIssue) {
        this.currentIssue = currentIssue;
    }

    public List<ChartRun> getChartRuns() {
        return chartRuns;
    }

    public void setChartRuns(List<ChartRun> chartRuns) {
        this.chartRuns = chartRuns;
    }
}

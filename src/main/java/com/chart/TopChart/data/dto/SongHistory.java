package com.chart.TopChart.data.dto;

import java.util.List;

public class SongHistory {

    private Integer peak;
    private Long currentChart;
    private List<ChartRun> chartRuns;

    public SongHistory() {
    }

    public SongHistory(Integer peak, Long currentChart, List<ChartRun> chartRuns) {
        this.peak = peak;
        this.currentChart = currentChart;
        this.chartRuns = chartRuns;
    }

    public Integer getPeak() {
        return peak;
    }

    public void setPeak(Integer peak) {
        this.peak = peak;
    }

    public Long getCurrentChart() {
        return currentChart;
    }

    public void setCurrentChart(Long currentChart) {
        this.currentChart = currentChart;
    }

    public List<ChartRun> getChartRuns() {
        return chartRuns;
    }

    public void setChartRuns(List<ChartRun> chartRuns) {
        this.chartRuns = chartRuns;
    }
}

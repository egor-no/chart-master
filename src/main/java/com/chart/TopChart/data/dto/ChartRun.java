package com.chart.TopChart.data.dto;

import java.util.List;

public class ChartRun {

    private List<Integer> positions;
    private ChartBasic firstChart;
    private ChartBasic lastChart;

    public ChartRun() {
    }

    public ChartRun(List<Integer> positions, ChartBasic firstChart, ChartBasic lastChart) {
        this.positions = positions;
        this.firstChart = firstChart;
        this.lastChart = lastChart;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
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
}

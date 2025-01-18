package com.chart.TopChart.data.dto;

import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.Position;

import java.util.List;

public class ChartRun {

    private List<Position> positions;
    private Chart firstChart;
    private Chart lastChart;

    public ChartRun() {
    }

    public ChartRun(List<Position> positions, Chart firstChart, Chart lastChart) {
        this.positions = positions;
        this.firstChart = firstChart;
        this.lastChart = lastChart;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public Chart getFirstChart() {
        return firstChart;
    }

    public void setFirstChart(Chart firstChart) {
        this.firstChart = firstChart;
    }

    public Chart getLastChart() {
        return lastChart;
    }

    public void setLastChart(Chart lastChart) {
        this.lastChart = lastChart;
    }
}

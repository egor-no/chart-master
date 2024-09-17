package com.chart.TopChart.service;

import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.Position;
import java.util.List;

public class ChartFull {

    private long id;
    private String date;
    private List<Position> positions;
    private List<Long> woc;
    private List<Long> peaks;

    public ChartFull() {
    }

    public ChartFull(long id, String date, List<Position> positions, List<Long> woc, List<Long> peaks) {
        this.id = id;
        this.date = date;
        this.positions = positions;
        this.woc = woc;
        this.peaks = peaks;
    }

    public ChartFull(Chart chart) {
        this.id = chart.getId();
        this.date = chart.getDate();
        this.positions = chart.getPositions();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public List<Long> getWoc() {
        return woc;
    }

    public void setWoc(List<Long> woc) {
        this.woc = woc;
    }

    public List<Long> getPeaks() {
        return peaks;
    }

    public void setPeaks(List<Long> peaks) {
        this.peaks = peaks;
    }
}

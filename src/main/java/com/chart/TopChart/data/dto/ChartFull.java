package com.chart.TopChart.data.dto;

import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.Position;
import java.util.List;

public class ChartFull {

    private int issueNumber;
    private String date;
    private List<Position> positions;
    private List<Long> woc;
    private List<Long> peaks;
    private List<ChartOutsider> outsiders;

    public ChartFull() {
    }

    public ChartFull(int issueNumber, String date, List<Position> positions, List<Long> woc, List<Long> peaks, List<ChartOutsider> outsiders) {
        this.issueNumber = issueNumber;
        this.date = date;
        this.positions = positions;
        this.woc = woc;
        this.peaks = peaks;
        this.outsiders = outsiders;
    }

    public ChartFull(Chart chart) {
        this.issueNumber = chart.getIssueNumber();
        this.date = chart.getDate();
        this.positions = chart.getPositions();
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

    public List<ChartOutsider> getOutsiders() {
        return outsiders;
    }

    public void setOutsiders(List<ChartOutsider> outsiders) {
        this.outsiders = outsiders;
    }
}

package com.chart.TopChart.data.dto;

public class ChartStats {
    private int highestClimb;
    private int biggestFall;
    private int newEntries;
    private int reEntries;

    public int getHighestClimb() {
        return highestClimb;
    }

    public void setHighestClimb(int highestClimb) {
        this.highestClimb = highestClimb;
    }

    public int getBiggestFall() {
        return biggestFall;
    }

    public void setBiggestFall(int biggestFall) {
        this.biggestFall = biggestFall;
    }

    public int getNewEntries() {
        return newEntries;
    }

    public void setNewEntries(int newEntries) {
        this.newEntries = newEntries;
    }

    public int getReEntries() {
        return reEntries;
    }

    public void setReEntries(int reEntries) {
        this.reEntries = reEntries;
    }
}
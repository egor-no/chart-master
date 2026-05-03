package com.chart.TopChart.data.dto;

import com.chart.TopChart.data.model.Position;

public class ChartOutsider {

    private Position position;
    private Long peak;
    private Long woc;

    public ChartOutsider() {
    }

    public ChartOutsider(Position position, Long peak, Long woc) {
        this.position = position;
        this.peak = peak;
        this.woc = woc;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Long getPeak() {
        return peak;
    }

    public void setPeak(Long peak) {
        this.peak = peak;
    }

    public Long getWoc() {
        return woc;
    }

    public void setWoc(Long woc) {
        this.woc = woc;
    }
}
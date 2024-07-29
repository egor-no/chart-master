package com.chart.TopChart.data.model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class Position_PK implements Serializable {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Chart")
    private Chart chart;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSong")
    private Song song;

    public Position_PK() {
    }

    public Position_PK(Chart chart, Song song) {
        this.chart = chart;
        this.song = song;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}

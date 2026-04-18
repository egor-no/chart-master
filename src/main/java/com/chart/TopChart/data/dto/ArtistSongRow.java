package com.chart.TopChart.data.dto;

import com.chart.TopChart.data.model.Song;

public class ArtistSongRow {

    private Song song;
    private String firstEntryDate;

    public ArtistSongRow() {
    }

    public ArtistSongRow(Song song, String firstEntryDate) {
        this.song = song;
        this.firstEntryDate = firstEntryDate;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getFirstEntryDate() {
        return firstEntryDate;
    }

    public void setFirstEntryDate(String firstEntryDate) {
        this.firstEntryDate = firstEntryDate;
    }
}
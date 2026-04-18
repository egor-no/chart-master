package com.chart.TopChart.data.dto;

import com.chart.TopChart.data.model.Song;

public class ArtistSongRow {

    private Song song;
    private String firstEntryDate;
    private String firstEntryDateSortable;

    public ArtistSongRow() {
    }

    public ArtistSongRow(Song song, String firstEntryDate, String firstEntryDateSortable) {
        this.song = song;
        this.firstEntryDate = firstEntryDate;
        this.firstEntryDateSortable = firstEntryDateSortable;
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

    public String getFirstEntryDateSortable() {
        return firstEntryDateSortable;
    }

    public void setFirstEntryDateSortable(String firstEntryDateSortable) {
        this.firstEntryDateSortable = firstEntryDateSortable;
    }
}
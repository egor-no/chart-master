package com.chart.TopChart.data.model;

import javax.persistence.*;

@Entity
@Table(name="song")
public class Song {

    @Id
    @Column(name="idSong")
    private int id;

    @Column(name="Weeks")
    private int weeks;

    @Column(name="Peak")
    private int peak;

    @Column(name="Artists")
    private String artists;

    @Column(name="Name")
    private String name;

    public Song() {
    }

    public Song(int id, int weeks, int peak, String artists, String name) {
        this.id = id;
        this.weeks = weeks;
        this.peak = peak;
        this.artists = artists;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public int getPeak() {
        return peak;
    }

    public void setPeak(int peak) {
        this.peak = peak;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", weeks=" + weeks +
                ", peak=" + peak +
                ", artists='" + artists + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

package com.chart.TopChart.data.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="song")
public class Song {

    @Id
    @Column(name="idSong")
    private long id;

    @Column(name="Weeks")
    private int weeks;

    @Column(name="Peak")
    private int peak;

    @Column(name="Artists")
    private String artists;

    @Column(name="Name")
    private String name;

    @OneToMany(mappedBy = "pk.song", cascade = CascadeType.ALL)
    private List<Position> positions;

    public Song() {
    }

    public Song(long id, int weeks, int peak, String artists, String name, List<Position> positions) {
        this.id = id;
        this.weeks = weeks;
        this.peak = peak;
        this.artists = artists;
        this.name = name;
        this.positions = positions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
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

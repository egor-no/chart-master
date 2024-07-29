package com.chart.TopChart.data.model;

import javax.persistence.*;

@Entity
@Table(name="position")
public class Position {

    @EmbeddedId
    private Position_PK pk;

    @Column(name="Position")
    private int position;

    @Column(name="LastWeek")
    private Integer lastWeek;

    public Position() {
    }

    public Position(Position_PK pk, int position, Integer lastWeek) {
        this.pk = pk;
        this.position = position;
        this.lastWeek = lastWeek;
    }

    public Position_PK getPk() {
        return pk;
    }

    public void setPk(Position_PK pk) {
        this.pk = pk;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Integer getLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(Integer lastWeek) {
        this.lastWeek = lastWeek;
    }

    @Override
    public String toString() {
        return "Position{" +
                "pk=" + pk +
                ", position=" + position +
                ", lastWeek=" + lastWeek +
                '}';
    }
}

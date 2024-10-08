package com.chart.TopChart.data.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="chart")
public class Chart {

    @Id
    @Column(name="idChart")
    private long id;

    @Column(name="Date")
    private String date;

    @OneToMany(mappedBy = "pk.chart", cascade = CascadeType.ALL)
    private List<Position> positions;

    public Chart() {
    }

    public Chart(long id, String date, List<Position> positions) {
        this.id = id;
        this.date = date;
        this.positions = positions;
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

    @Override
    public String toString() {
        return "Chart{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }
}

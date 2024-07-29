package com.chart.TopChart.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="chart")
public class Chart {

    @Id
    @Column(name="idChart")
    private int id;

    @Column(name="Date")
    private String date;

    public Chart() {
    }

    public Chart(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Chart{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }
}

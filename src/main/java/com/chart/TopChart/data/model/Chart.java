package com.chart.TopChart.data.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="chart")
public class Chart {

    @Id
    @Column(name="idChart")
    private long id;

    @Column(name = "issue_number")
    private Integer issueNumber;

    @Column(name="Date")
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idinfo")
    private ChartInfo info;

    @OneToMany(mappedBy = "pk.chart", cascade = CascadeType.ALL)
    private List<Position> positions;

    public Chart() {
    }

    public Chart(long id, Integer issueNumber, String date, ChartInfo info, List<Position> positions) {
        this.id = id;
        this.issueNumber = issueNumber;
        this.date = date;
        this.info = info;
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

    public ChartInfo getInfo() {
        return info;
    }

    public void setInfo(ChartInfo info) {
        this.info = info;
    }

    public Integer getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Integer issueNumber) {
        this.issueNumber = issueNumber;
    }

    @Override
    public String toString() {
        return "Chart{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }
}

package com.chart.TopChart.data.dto;

import javax.persistence.Column;

public class ChartBasic {

    private long id;

    private String date;

    public ChartBasic() {
    }

    public ChartBasic(long id, String date) {
        this.id = id;
        this.date = date;
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
}

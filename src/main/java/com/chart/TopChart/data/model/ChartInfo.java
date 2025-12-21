package com.chart.TopChart.data.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="chart_info")
public class ChartInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idChartInfo")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private User owner;

    @Column(name="title")
    private String title;

    @Column(name="size")
    private int size;

    @Column(name="description")
    private String description;

    @Column(name="fieldsNumber")
    private Integer fieldsAmount;

    @Column(name="field1Name")
    private String field1Name;

    @Column(name="field2Name")
    private String field2Name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ChartInfo() {
    }

    public ChartInfo(int id, User owner, String title, int size, String description, Integer fieldsAmount, String field1Name, String field2Name, LocalDateTime createdAt) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.size = size;
        this.description = description;
        this.fieldsAmount = fieldsAmount;
        this.field1Name = field1Name;
        this.field2Name = field2Name;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFieldsAmount() {
        return fieldsAmount;
    }

    public void setFieldsAmount(Integer fieldsAmount) {
        this.fieldsAmount = fieldsAmount;
    }

    public String getField1Name() {
        return field1Name;
    }

    public void setField1Name(String field1Name) {
        this.field1Name = field1Name;
    }

    public String getField2Name() {
        return field2Name;
    }

    public void setField2Name(String field2Name) {
        this.field2Name = field2Name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

package com.chart.TopChart.data.dto;

public class HomeChartInfoRow {
    private int id;
    private String title;
    private String description;
    private int size;
    private String ownerNickname;
    private int ownerId;
    private long issuesCount;

    public HomeChartInfoRow(int id, String title, String description, int size,
                            String ownerNickname, int ownerId, long issuesCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.size = size;
        this.ownerNickname = ownerNickname;
        this.ownerId = ownerId;
        this.issuesCount = issuesCount;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getSize() { return size; }
    public String getOwnerNickname() { return ownerNickname; }
    public int getOwnerId() { return ownerId; }
    public long getIssuesCount() { return issuesCount; }
}
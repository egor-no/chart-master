package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dto.ChartBasic;
import com.chart.TopChart.data.dto.ChartRun;
import com.chart.TopChart.data.dto.SongHistory;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.Position;

import java.util.ArrayList;
import java.util.List;

public class SongHistoryService {

    private static ChartBasic converChartToBasic(Chart chart) {
        return new ChartBasic(chart.getIssueNumber(), chart.getDate());
    }

    public static SongHistory getSongHistory(int chartInfoId, long idSong, String sDate1, String sDate2) {
        SongHistory songHistory = new SongHistory();

        List<Position> positions;
        if (sDate1 != null && !sDate1.isEmpty()) {
            positions = PositionDAOImpl.getPositionsForSongByDate(chartInfoId, idSong, sDate1, sDate2);
        } else {
            positions = PositionDAOImpl.getPositionsForSong(chartInfoId, idSong);
        }

        Integer peak = null;
        for (Position position : positions) {
            if (peak == null || position.getPosition() < peak) {
                peak = position.getPosition();
            }
        }
        songHistory.setPeak(peak);

        List<ChartRun> chartRuns = new ArrayList<>();
        List<ChartRun.ChartRunPosition> chartRunPositions = new ArrayList<>();

        ChartRun chartRun = new ChartRun();
        chartRun.setPositions(chartRunPositions);

        Chart firstChart = null;

        for (int i = 0; i < positions.size(); i++) {
            Position position = positions.get(i);
            Chart chart = position.getPk().getChart();

            chartRunPositions.add(new ChartRun.ChartRunPosition(
                    position.getPosition(),
                    chart.getIssueNumber(),
                    chart.getDate()
            ));

            if (firstChart == null) {
                firstChart = chart;
            }

            if (i == positions.size() - 1 ||
                    positions.get(i + 1).getPk().getChart().getIssueNumber() - chart.getIssueNumber() != 1) {

                chartRun.setFirstChart(converChartToBasic(firstChart));
                chartRun.setLastChart(converChartToBasic(chart));
                chartRuns.add(chartRun);

                chartRun = new ChartRun();
                firstChart = null;
                chartRunPositions = new ArrayList<>();
                chartRun.setPositions(chartRunPositions);
            }
        }

        songHistory.setChartRuns(chartRuns);
        return songHistory;
    }
}
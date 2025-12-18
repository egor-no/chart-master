package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.dto.ChartBasic;
import com.chart.TopChart.data.dto.ChartRun;
import com.chart.TopChart.data.dto.SongHistory;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.Position;
import com.chart.TopChart.data.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongHistoryService {

    private static ChartBasic converChartToBasic(Chart chart) {
        ChartBasic chartBasic = new ChartBasic(chart.getId(), chart.getDate());
        return chartBasic;
    }

    public static SongHistory getSongHistory(int chartInfoId, long idSong, String sDate1, String sDate2) {
        SongHistory songHistory = new SongHistory();
        Song song = SongDAOImpl.getById(idSong);
        songHistory.setPeak(song.getPeak());

        List<Position> positions;
        if (sDate1 != null && sDate2 != null) {
            positions = PositionDAOImpl.getPositionsForSongByDate(chartInfoId, idSong, sDate1, sDate2);
        } else {
            positions = PositionDAOImpl.getPositionsForSong(chartInfoId, idSong);
        }
        List<ChartRun> chartRuns = new ArrayList<>();
        List<Integer> chartRunPositions = new ArrayList<>();
        ChartRun chartRun =  new ChartRun();
        chartRun.setPositions(chartRunPositions);
        Chart firstChart = null;

        for (int i = 0; i < positions.size(); i++) {
            chartRunPositions.add(positions.get(i).getPosition());
            if (firstChart == null) {
                firstChart = positions.get(i).getPk().getChart();
            }
            if (i == positions.size() - 1 ||
                    positions.get(i+1).getPk().getChart().getId() - positions.get(i).getPk().getChart().getId() != 1) {
                chartRun.setFirstChart(converChartToBasic(firstChart));
                chartRun.setLastChart(converChartToBasic(positions.get(i).getPk().getChart()));
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

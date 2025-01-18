package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dto.ChartRun;
import com.chart.TopChart.data.model.Position;

import java.util.ArrayList;
import java.util.List;

public class ChartRunService {

    public static List<ChartRun> getChartRuns(long idSong) {
        List<Position> positions = PositionDAOImpl.getPositionsForSong(idSong);
        List<ChartRun> chartRuns = new ArrayList<>();
        List<Position> chartRunPositions = new ArrayList<>();
        ChartRun chartRun =  new ChartRun();

        for (int i = 0; i < positions.size(); i++) {
            chartRunPositions.add(positions.get(i));
            if (i == positions.size() - 1 ||
                    positions.get(i+1).getPk().getChart().getId() - positions.get(i).getPk().getChart().getId() != 1) {
                chartRun.setFirstChart(chartRunPositions.get(0).getPk().getChart());
                chartRun.setLastChart(chartRunPositions.get(chartRunPositions.size()-1).getPk().getChart());
                chartRuns.add(chartRun);

                chartRun = new ChartRun();
                chartRunPositions = new ArrayList<>();
            }
        }

        return chartRuns;
    }
}

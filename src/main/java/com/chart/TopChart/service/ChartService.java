package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.model.Chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartService {

    public static ChartFull getChartFull(Chart chart) {
        ChartFull chartFull = new ChartFull(chart);

        List<Long> songIds = new ArrayList<>();
        for (int i = 0; i < chart.getPositions().size(); i++) {
            songIds.add(chart.getPositions().get(i).getPk().getSong().getId());
        }

        List wocList = PositionDAOImpl.getWOCforChart(chart.getId(), songIds);
        List peaksList = PositionDAOImpl.getPeaksForChart(chart.getId(), songIds);

        Map<Long, Long> wocMap = new HashMap<>();
        Map<Long, Long> peaksMap = new HashMap<>();
        for (int i = 0; i < wocList.size(); i++) {
            wocMap.put(Long.parseLong(((Object[])wocList.get(i))[0].toString()), Long.parseLong(((Object[])wocList.get(i))[1].toString()));
            peaksMap.put(Long.parseLong(((Object[])peaksList.get(i))[0].toString()), Long.parseLong(((Object[])peaksList.get(i))[1].toString()));
        }

        List<Long> woc = new ArrayList<>();
        List<Long> peaks = new ArrayList<>();
        for (int i = 0; i < chart.getPositions().size(); i++) {
            woc.add(wocMap.get(chart.getPositions().get(i).getPk().getSong().getId()));
            peaks.add(peaksMap.get(chart.getPositions().get(i).getPk().getSong().getId()));
        }

        chartFull.setPeaks(peaks);
        chartFull.setWoc(woc);

        return chartFull;
    }
}

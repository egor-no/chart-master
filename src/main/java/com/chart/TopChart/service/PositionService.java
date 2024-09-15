package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.model.Chart;

import java.util.ArrayList;
import java.util.List;

public class PositionService {

    public static List<Long> getWOCforChart(Chart chart) {
        List<Long> woc = new ArrayList<>();
        for (int i = 0; i < chart.getPositions().size(); i++) {
            woc.add(PositionDAOImpl.getWocByChart(chart.getId(), chart.getPositions().get(i).getPk().getSong().getId()));
        }
        return woc;
    }
}

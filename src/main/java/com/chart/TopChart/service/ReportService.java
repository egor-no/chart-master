package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.PositionDAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    public static List<List<String>> getLongestWaysToTop10() {
        List<Object[]> rows = PositionDAOImpl.getSongPositionsRowsAllTime();

        class Acc {
            long songId;
            String artists;
            String name;
            Long debutChartId = null;
            Long firstTop10ChartId = null;
            List<Long> chartIds = new ArrayList<>();
            Acc(long songId, String artists, String name){
                this.songId = songId; this.artists = artists; this.name = name;
            }
        }

        Map<Long, Acc> map = new HashMap<>();

        for (Object[] r : rows) {
            Long songId = (Long) r[0];
            Long chartId = (Long) r[1];
            Integer pos = (Integer) r[2];
            String artists = (String) r[3];
            String name = (String) r[4];
            if (songId == null || chartId == null || pos == null) continue;
            Acc a = map.computeIfAbsent(songId, k -> new Acc(songId, artists, name));
            if (a.debutChartId == null) a.debutChartId = chartId;
            a.chartIds.add(chartId);
            if (pos <= 10 && a.firstTop10ChartId == null) a.firstTop10ChartId = chartId;
        }

        List<List<String>> out = new ArrayList<>();

        for (Acc a : map.values()) {
            if (a.debutChartId == null || a.firstTop10ChartId == null) continue;

            if (a.firstTop10ChartId.equals(a.debutChartId)) continue;

            long calendarWeeks = a.firstTop10ChartId - a.debutChartId + 1;

            int actualWeeks = 0;
            for (Long cid : a.chartIds) {
                if (cid >= a.debutChartId && cid <= a.firstTop10ChartId) actualWeeks++;
            }

            List<String> row = new ArrayList<>();
            row.add(String.valueOf(actualWeeks));              // [0]
            row.add(String.valueOf(calendarWeeks));            // [1]
            row.add(a.artists);                                // [2]
            row.add(a.name);                                   // [3]
            row.add(String.valueOf(a.songId));                 // [4]
            row.add(String.valueOf(a.debutChartId));           // [5]
            row.add(String.valueOf(a.firstTop10ChartId));      // [6]
            out.add(row);
        }

        out.sort((x, y) -> {
            int a1 = Integer.parseInt(x.get(0)), b1 = Integer.parseInt(y.get(0));
            if (b1 != a1) return b1 - a1;
            int a2 = Integer.parseInt(x.get(1)), b2 = Integer.parseInt(y.get(1));
            return b2 - a2;
        });

        if (out.size() > 50) out.subList(50, out.size()).clear();
        return out;
    }

}

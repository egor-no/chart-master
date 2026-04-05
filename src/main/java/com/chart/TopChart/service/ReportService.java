package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.PositionDAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    public static List<List<String>> getLongestWaysToTop10(int chartInfoId) {
        List<Object[]> rows = PositionDAOImpl.getSongPositionsRowsAllTime(chartInfoId);

        class Acc {
            long songId;
            String artists;
            String name;
            Integer debutIssueNumber = null;
            Integer firstTop10IssueNumber = null;
            List<Integer> issueNumbers = new ArrayList<>();

            Acc(long songId, String artists, String name) {
                this.songId = songId;
                this.artists = artists;
                this.name = name;
            }
        }

        Map<Long, Acc> map = new HashMap<>();

        for (Object[] r : rows) {
            Long songId = (Long) r[0];
            Integer issueNumber = (Integer) r[1];
            Integer pos = (Integer) r[2];
            String artists = (String) r[3];
            String name = (String) r[4];

            if (songId == null || issueNumber == null || pos == null) continue;

            Acc a = map.computeIfAbsent(songId, k -> new Acc(songId, artists, name));

            if (a.debutIssueNumber == null) a.debutIssueNumber = issueNumber;
            a.issueNumbers.add(issueNumber);

            if (pos <= 10 && a.firstTop10IssueNumber == null) {
                a.firstTop10IssueNumber = issueNumber;
            }
        }

        List<List<String>> out = new ArrayList<>();

        for (Acc a : map.values()) {
            if (a.debutIssueNumber == null || a.firstTop10IssueNumber == null) continue;
            if (a.firstTop10IssueNumber.equals(a.debutIssueNumber)) continue;

            int calendarWeeks = a.firstTop10IssueNumber - a.debutIssueNumber + 1;

            int actualWeeks = 0;
            for (Integer issueNumber : a.issueNumbers) {
                if (issueNumber >= a.debutIssueNumber && issueNumber <= a.firstTop10IssueNumber) {
                    actualWeeks++;
                }
            }

            List<String> row = new ArrayList<>();
            row.add(String.valueOf(actualWeeks));                 // [0]
            row.add(String.valueOf(calendarWeeks));               // [1]
            row.add(a.artists);                                   // [2]
            row.add(a.name);                                      // [3]
            row.add(String.valueOf(a.songId));                    // [4]
            row.add(String.valueOf(a.debutIssueNumber));          // [5]
            row.add(String.valueOf(a.firstTop10IssueNumber));     // [6]
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

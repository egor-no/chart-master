package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.ChartInfoDAOImpl;
import com.chart.TopChart.data.dao.UserDAOImpl;
import com.chart.TopChart.data.dto.HomeUpdateRow;

import java.util.ArrayList;
import java.util.List;

public class HomeUpdatesService {

    public static List<HomeUpdateRow> getLatestUpdates(int limit) {
        int take = Math.max(10, limit * 2);

        List<HomeUpdateRow> users = UserDAOImpl.getLatestUsersForUpdates(take);
        List<HomeUpdateRow> chartInfos = ChartInfoDAOImpl.getLatestChartInfosForUpdates(take);
        List<HomeUpdateRow> issues = ChartDAOImpl.getLatestIssuesForUpdates(take);

        ArrayList<HomeUpdateRow> all = new ArrayList<>(users.size() + chartInfos.size() + issues.size());
        all.addAll(users);
        all.addAll(chartInfos);
        all.addAll(issues);

        all.sort((a, b) -> {
            if (a.getSortTime() == null && b.getSortTime() == null) return 0;
            if (a.getSortTime() == null) return 1;
            if (b.getSortTime() == null) return -1;
            return b.getSortTime().compareTo(a.getSortTime());
        });

        if (all.size() > limit) return all.subList(0, limit);
        return all;
    }
}
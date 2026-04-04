package com.chart.TopChart.service.profile;

import com.chart.TopChart.data.dto.ChartBasic;
import com.chart.TopChart.data.model.ChartInfo;

import java.util.List;

public class ProfileChartCard {
    private final ChartInfo ci;
    private final List<ChartBasic> lastIssues;

    public ProfileChartCard(ChartInfo ci, List<ChartBasic> lastIssues) {
        this.ci = ci;
        this.lastIssues = lastIssues;
    }

    public ChartInfo getCi() {
        return ci;
    }

    public List<ChartBasic> getLastIssues() {
        return lastIssues;
    }
}

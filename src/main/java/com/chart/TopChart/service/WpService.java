package com.chart.TopChart.service;

import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.Position;

import java.util.List;

public class WpService {

    public static String formWpCode(Chart chart) {
        StringBuilder sb = new StringBuilder();
        List<Position> positions = chart.getPositions();

        for (int i = 40; i >= 1; i--) {
            Position position = positions.get(i-1);
            sb.append("<tr><td>");
            String lw;
            if (position.getLastWeek() == null) {
                lw = "";
            } else if (position.getLastWeek() - position.getPosition() == 0) {
                lw = "=";
            } else if (position.getLastWeek() - position.getPosition() > 0) {
                lw = "+" + (position.getLastWeek() - position.getPosition());
            } else {
                lw = String.valueOf(position.getLastWeek() - position.getPosition());
            }
            sb.append(lw);
            sb.append("</td>");
            sb.append("<td>");
            sb.append(position.getPosition());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(position.getPk().getSong().getArtists() + " - " + position.getPk().getSong().getName());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(position.getPk().getSong().getPeak());
            sb.append("</td>");
            sb.append("<td>");
            sb.append(position.getPk().getSong().getWeeks());
            sb.append("</td></tr>\n");
        }
        return sb.toString().trim();
    }
}

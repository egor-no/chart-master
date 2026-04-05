package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.ChartInfoDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.dto.ChartFull;
import com.chart.TopChart.data.model.*;
import util.DateUtil;

import java.util.*;

public class ChartService {

    public static ChartFull getChartFull(Chart chart) {
        int chartInfoId = chart.getInfo().getId();
        ChartFull chartFull = new ChartFull(chart);

        List<Long> songIds = new ArrayList<>();
        for (int i = 0; i < chart.getPositions().size(); i++) {
            songIds.add(chart.getPositions().get(i).getPk().getSong().getId());
        }

        List wocList = PositionDAOImpl.getWOCforChart(chartInfoId, chart.getIssueNumber(), songIds);
        List peaksList = PositionDAOImpl.getPeaksForChart(chartInfoId, chart.getIssueNumber(), songIds);

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

    public static String getNewChartDate(int chartInfoId) {
        Integer lastIssueNumber = ChartDAOImpl.getLastIssueNumber(chartInfoId);

        if (lastIssueNumber == null) {
            return DateUtil.formatDateForSQL(new Date());
        }

        String lastSDate = ChartDAOImpl.getByIssueNumber(chartInfoId, lastIssueNumber).getDate();
        Date lastDate = DateUtil.parseStringToSqlDate(lastSDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(lastDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);

        return DateUtil.formatDateForSQL(cal.getTime());
    }

    public static boolean deleteChart(int issueNumber, int chartInfoId) {
        if (issueNumber != ChartDAOImpl.getLastIssueNumber(chartInfoId)) return false;

        try {
            Chart chart = ChartDAOImpl.getByIssueNumber(chartInfoId, issueNumber);
            if (chart == null || chart.getPositions() == null) return false;

            Set<Long> songIds = new HashSet<>();
            for (Position p : chart.getPositions()) {
                if (p != null && p.getPk() != null && p.getPk().getSong() != null) {
                    songIds.add(p.getPk().getSong().getId());
                }
            }

            ChartDAOImpl.delete(chartInfoId, issueNumber);

            for (Long songId : songIds) {
                List<Position> remaining = PositionDAOImpl.getPositionsForSong(chartInfoId, songId);

                Song song = SongDAOImpl.getById(songId);
                if (song == null) continue;
                song.setWeeks(remaining.size());
                if (remaining.isEmpty()) {
                    song.setPeak(41);
                } else {
                    int minPos = Integer.MAX_VALUE;
                    for (Position rp : remaining) {
                        if (rp.getPosition() < minPos) minPos = rp.getPosition();
                    }
                    song.setPeak(minPos);
                }
                SongDAOImpl.update(song);
            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static void formChart(int chartInfoId, String ids[], String name[], String artists[]) {
        Long lastChartIdObj = ChartDAOImpl.getLastId();
        long lastChartId = (lastChartIdObj == null) ? 0L : lastChartIdObj;

        Integer lastChartIssueObj = ChartDAOImpl.getLastIssueNumber(chartInfoId);
        int lastChartIssueNumber = (lastChartIssueObj == null) ? 0 : lastChartIssueObj;

        Chart chart = new Chart();
        chart.setDate(ChartService.getNewChartDate(chartInfoId));
        chart.setId(lastChartId+1);
        chart.setIssueNumber(lastChartIssueNumber+1);

        ChartInfo info = ChartInfoDAOImpl.getById(chartInfoId);
        chart.setInfo(info);
        ChartDAOImpl.save(chart);

        Position position;
        Position_PK position_pk;
        Song song;
        for (int i = 0; i < ids.length; i++) {
            final int pos = i + 1;
            song = new Song();
            try {
                Long id = Long.parseLong(ids[i]);
                song = SongDAOImpl.getById(id);
                song.setWeeks(song.getWeeks() + 1);
                if (song.getPeak() > pos) {
                    song.setPeak(pos);
                }
                SongDAOImpl.update(song);
            } catch (Exception ex) {
                song.setArtists(artists[i]);
                song.setName(name[i]);
                song.setWeeks(1);
                song.setPeak(pos);
                song.setId(SongDAOImpl.getLastId()+1);
                SongDAOImpl.save(song);
            }

            position_pk = new Position_PK();
            position_pk.setChart(chart);
            position_pk.setSong(song);

            position = new Position();
            position.setPosition(pos);
            position.setPk(position_pk);
            if (lastChartIssueNumber > 0) {
                Position prev = PositionDAOImpl.getPositionForSong(chartInfoId, song.getId(), lastChartIssueNumber);
                if (prev != null) position.setLastWeek(prev.getPosition());
            }

            PositionDAOImpl.save(position);
        }
    }
}

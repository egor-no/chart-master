package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.dto.ChartFull;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.Position;
import com.chart.TopChart.data.model.Position_PK;
import com.chart.TopChart.data.model.Song;
import util.DateUtil;

import java.util.*;

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

    public static String getNewChartDate() {
        String lastSDate = ChartDAOImpl.getById(ChartDAOImpl.getLastId()).getDate();
        Date lastDate = DateUtil.parseStringToSqlDate(lastSDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        return DateUtil.formatDateForSQL(cal.getTime());
    }

    public static boolean deleteChart(long chartId) {
        try {
            ChartDAOImpl.delete(chartId);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static void formChart(String ids[], String name[], String artists[]) {
        long lastChartId = ChartDAOImpl.getLastId();

        Chart chart = new Chart();
        chart.setDate(ChartService.getNewChartDate());
        chart.setId(lastChartId+1);
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
            Position prevSongPosition = PositionDAOImpl.getPositionForSong(song.getId(), lastChartId);
            if (prevSongPosition != null) {
                position.setLastWeek(prevSongPosition.getPosition());
            }

            PositionDAOImpl.save(position);
        }
    }
}

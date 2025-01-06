package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.SongDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class ArtistSongService {

    public static List<Long> getArtistTopStats(String artist) {
        long no1s = SongDAOImpl.getArtistTopStat(artist, 1);
        long top10s = SongDAOImpl.getArtistTopStat(artist, 10);
        long top40s = SongDAOImpl.getArtistTopStat(artist, 40);

        List<Long> topStats = new ArrayList<>();
        topStats.add(no1s);
        topStats.add(top10s);
        topStats.add(top40s);

        return topStats;
    }
}

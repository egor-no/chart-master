package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

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

    public static List getTopArtistsBySongs() {
        List<String> artists = SongDAOImpl.getArtists();
        List<List<String>> artistStats = new ArrayList();
        for (int i = 0; i < artists.size(); i++) {
            List<Long> stats = getArtistTopStats(artists.get(i));
            artistStats.add(new ArrayList<>());
            artistStats.get(i).add(artists.get(i));
            artistStats.get(i).add(String.valueOf(stats.get(0)));
            artistStats.get(i).add(String.valueOf(stats.get(1)));
            artistStats.get(i).add(String.valueOf(stats.get(2)));
        }

        artistStats.sort((a, b) -> {
            int stat0A = Integer.parseInt(a.get(1));
            int stat0B = Integer.parseInt(b.get(1));
            if (stat0B != stat0A) return stat0B - stat0A;

            int stat1A = Integer.parseInt(a.get(2));
            int stat1B = Integer.parseInt(b.get(2));
            if (stat1B != stat1A) return stat1B - stat1A;

            int stat2A = Integer.parseInt(a.get(3));
            int stat2B = Integer.parseInt(b.get(3));
            return stat2B - stat2A;
        });

        artistStats.subList(50, artistStats.size()).clear();

        return artistStats;
    }

    public static List<Long> getArtistLength(String artist) {
        long no1s = PositionDAOImpl.getArtistTopLength(artist, 1);
        long top10s = PositionDAOImpl.getArtistTopLength(artist, 10);
        long top40s = PositionDAOImpl.getArtistTopLength(artist, 40);

        List<Long> lengthStats = new ArrayList<>();
        lengthStats.add(no1s);
        lengthStats.add(top10s);
        lengthStats.add(top40s);

        return lengthStats;
    }

    public static List getTopArtists() {
        List<String> artists = SongDAOImpl.getArtists();

        List<List<String>> artistStats = new ArrayList();
        for (int i = 0; i < artists.size(); i++) {
            long score = SongDAOImpl.getArtistScore(artists.get(i));
            artistStats.add(new ArrayList<>());
            artistStats.get(i).add(artists.get(i));
            artistStats.get(i).add(String.valueOf(score));
        }

        artistStats.sort((a, b) -> {
            int stat0A = Integer.parseInt(a.get(1));
            int stat0B = Integer.parseInt(b.get(1));
            return stat0B - stat0A;
        });

        artistStats.subList(50, artistStats.size()).clear();

        for (int i = 0; i < artistStats.size(); i++) {
            List<Long> stats = getArtistLength(artistStats.get(i).get(0));
            artistStats.get(i).add(String.valueOf(stats.get(0)));
            artistStats.get(i).add(String.valueOf(stats.get(1)));
            artistStats.get(i).add(String.valueOf(stats.get(2)));
        }

        artistStats.sort((a, b) -> {
            int stat0A = Integer.parseInt(a.get(1));
            int stat0B = Integer.parseInt(b.get(1));
            if (stat0B != stat0A) return stat0B - stat0A;

            int stat1A = Integer.parseInt(a.get(2));
            int stat1B = Integer.parseInt(b.get(2));
            if (stat1B != stat1A) return stat1B - stat1A;

            int stat2A = Integer.parseInt(a.get(3));
            int stat2B = Integer.parseInt(b.get(3));
            if (stat2B != stat2A) return stat2B - stat2A;

            int stat3A = Integer.parseInt(a.get(4));
            int stat3B = Integer.parseInt(b.get(4));
            return stat3B - stat3A;
        });

        return artistStats;
    }
}

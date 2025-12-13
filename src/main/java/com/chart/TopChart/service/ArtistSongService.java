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

    public static List<Long> getArtistLengthByDate(String artist, String d1, String d2) {
        List<Long> lengthStats = new ArrayList<>();
        lengthStats.add(PositionDAOImpl.getArtistTopLengthByDate(artist, 1, d1, d2));
        lengthStats.add(PositionDAOImpl.getArtistTopLengthByDate(artist, 10, d1, d2));
        lengthStats.add(PositionDAOImpl.getArtistTopLengthByDate(artist, 40, d1, d2));
        return lengthStats;
    }

    public static List getTopArtists() {
        return buildTopArtists(SongDAOImpl.getArtists(), null, null, false);
    }

    public static List getTopArtistsByDate(String d1, String d2) {
        return buildTopArtists(PositionDAOImpl.getArtistsByDate(d1, d2), d1, d2, true);
    }

    private static List<List<String>> buildTopArtists(List<String> artists, String d1, String d2, boolean byDate) {
        List<List<String>> stats = new ArrayList<>();

        for (String artist : artists) {
            long score = byDate
                    ? SongDAOImpl.getArtistScoreByDate(artist, d1, d2)
                    : SongDAOImpl.getArtistScore(artist);

            List<String> row = new ArrayList<>();
            row.add(artist);
            row.add(String.valueOf(score));
            stats.add(row);
        }

        stats.sort((a, b) -> Integer.parseInt(b.get(1)) - Integer.parseInt(a.get(1)));
        if (stats.size() > 50) stats.subList(50, stats.size()).clear();

        for (List<String> row : stats) {
            List<Long> len = byDate
                    ? getArtistLengthByDate(row.get(0), d1, d2)
                    : getArtistLength(row.get(0));

            row.add(String.valueOf(len.get(0)));
            row.add(String.valueOf(len.get(1)));
            row.add(String.valueOf(len.get(2)));
        }

        stats.sort((a, b) -> {
            int sA = Integer.parseInt(a.get(1)), sB = Integer.parseInt(b.get(1));
            if (sB != sA) return sB - sA;

            int n1A = Integer.parseInt(a.get(2)), n1B = Integer.parseInt(b.get(2));
            if (n1B != n1A) return n1B - n1A;

            int t10A = Integer.parseInt(a.get(3)), t10B = Integer.parseInt(b.get(3));
            if (t10B != t10A) return t10B - t10A;

            int t40A = Integer.parseInt(a.get(4)), t40B = Integer.parseInt(b.get(4));
            return t40B - t40A;
        });

        return stats;
    }
}

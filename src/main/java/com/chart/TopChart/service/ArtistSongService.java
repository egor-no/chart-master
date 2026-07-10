package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Song;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ArtistSongService {

    public static List<Long> getArtistTopStatsFromSongs(List<Song> songs) {
        return buildArtistTopStatsFromSongs(songs);
    }

    private static List<Long> buildArtistTopStatsFromSongs(List<Song> songs) {
        long no1 = 0, top10 = 0, top40 = 0;

        for (Song s : songs) {
            if (s == null) continue;
            int peak = s.getPeak();
            if (peak == 1) no1++;
            if (peak <= 10) top10++;
            if (peak <= 40) top40++;
        }

        List<Long> res = new ArrayList<>(3);
        res.add(no1);
        res.add(top10);
        res.add(top40);
        return res;
    }

    public static List getTopArtistsBySongs(int chartInfoId, String sort) {
        return buildTopArtistsBySongs(SongDAOImpl.getArtistRowsForSongStatsAllTime(chartInfoId), sort);
    }

    static List<List<String>> buildTopArtistsBySongs(List<Object[]> rows, String sort) {
        java.util.Map<String, long[]> map = new java.util.HashMap<>();

        for (Object[] r : rows) {
            if (r == null || r.length < 3) continue;

            Integer peakObj = (Integer) r[1];
            String artistsRow = (String) r[2];
            if (peakObj == null || artistsRow == null) continue;

            int peak = peakObj;

            String[] parts = artistsRow.split("\\s*,\\s*");
            for (String p : parts) {
                if (p == null) continue;
                String artist = p.trim();
                if (artist.isEmpty()) continue;

                long[] a = map.computeIfAbsent(artist, k -> new long[3]);
                if (peak == 1) a[0]++;
                if (peak <= 10) a[1]++;
                if (peak <= 40) a[2]++;
            }
        }

        List<List<String>> stats = new ArrayList<>();
        for (java.util.Map.Entry<String, long[]> e : map.entrySet()) {
            long[] a = e.getValue();
            List<String> row = new ArrayList<>(4);
            row.add(e.getKey());
            row.add(String.valueOf(a[0]));
            row.add(String.valueOf(a[1]));
            row.add(String.valueOf(a[2]));
            stats.add(row);
        }

        final String sortMode = sort == null ? "no1" : sort;

        stats.sort((a, b) -> {
            long no1A = Long.parseLong(a.get(1)), no1B = Long.parseLong(b.get(1));
            long t10A = Long.parseLong(a.get(2)), t10B = Long.parseLong(b.get(2));
            long t40A = Long.parseLong(a.get(3)), t40B = Long.parseLong(b.get(3));

            if ("top10".equalsIgnoreCase(sortMode)) {
                if (t10B != t10A) return Long.compare(t10B, t10A);
                if (no1B != no1A) return Long.compare(no1B, no1A);
                return Long.compare(t40B, t40A);
            }

            if ("top40".equalsIgnoreCase(sortMode)) {
                if (t40B != t40A) return Long.compare(t40B, t40A);
                if (t10B != t10A) return Long.compare(t10B, t10A);
                return Long.compare(no1B, no1A);
            }

            if (no1B != no1A) return Long.compare(no1B, no1A);
            if (t10B != t10A) return Long.compare(t10B, t10A);
            return Long.compare(t40B, t40A);
        });

        if (stats.size() > 50) stats.subList(50, stats.size()).clear();
        return stats;
    }

    public static List getTopArtists(int chartInfoId) {
        return buildTopArtistsOnePass(PositionDAOImpl.getArtistRowsForStatsAllTime(chartInfoId));
    }

    public static List getTopArtistsByDate(int chartInfoId, String d1, String d2) {
        return buildTopArtistsOnePass(PositionDAOImpl.getArtistRowsForStatsByDate(chartInfoId, d1, d2));
    }

    static List<List<String>> buildTopArtistsOnePass(List<Object[]> rows) {
        java.util.Map<String, long[]> map = new java.util.HashMap<>();

        for (Object[] r : rows) {
            if (r == null || r.length < 2) continue;

            Integer posObj = (Integer) r[0];
            String artistsRow = (String) r[1];
            if (posObj == null || artistsRow == null) continue;

            int pos = posObj;
            long addScore = 41L - pos;

            String[] parts = artistsRow.split("\\s*,\\s*");
            for (String p : parts) {
                if (p == null) continue;
                String artist = p.trim();
                if (artist.isEmpty()) continue;

                long[] a = map.computeIfAbsent(artist, k -> new long[4]);
                a[0] += addScore;
                if (pos == 1) a[1]++;
                if (pos <= 10) a[2]++;
                if (pos <= 40) a[3]++;
            }
        }

        List<List<String>> stats = new ArrayList<>();
        for (java.util.Map.Entry<String, long[]> e : map.entrySet()) {
            long[] a = e.getValue();
            List<String> row = new ArrayList<>(5);
            row.add(e.getKey());
            row.add(String.valueOf(a[0]));
            row.add(String.valueOf(a[1]));
            row.add(String.valueOf(a[2]));
            row.add(String.valueOf(a[3]));
            stats.add(row);
        }

        stats.sort((a, b) -> {
            long sA = Long.parseLong(a.get(1)), sB = Long.parseLong(b.get(1));
            if (sB != sA) return Long.compare(sB, sA);

            long n1A = Long.parseLong(a.get(2)), n1B = Long.parseLong(b.get(2));
            if (n1B != n1A) return Long.compare(n1B, n1A);

            long t10A = Long.parseLong(a.get(3)), t10B = Long.parseLong(b.get(3));
            if (t10B != t10A) return Long.compare(t10B, t10A);

            long t40A = Long.parseLong(a.get(4)), t40B = Long.parseLong(b.get(4));
            return Long.compare(t40B, t40A);
        });

        if (stats.size() > 50) stats.subList(50, stats.size()).clear();
        return stats;
    }
}

package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.dto.ArtistSongRow;
import com.chart.TopChart.data.model.Song;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SongDAOImpl {

    public static long save(Song result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        long id = (Long) session.save(result);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    public static List<Song> getAll(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(
                "SELECT DISTINCT s " +
                        "FROM Position p " +
                        "JOIN p.pk.song s " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "ORDER BY s.id DESC");
        query.setInteger("ci", chartInfoId);

        List<Song> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static Song getById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Song s " +
                "WHERE s.id = :id");
        query.setLong("id", id);
        Song result = (Song) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static List<ArtistSongRow> getSongRowsBySearchPhrase(int chartInfoId, String searchPhrase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String qStr = (searchPhrase == null) ? "" : searchPhrase.trim().toLowerCase();

        Query query = session.createQuery(
                "SELECT s, MIN(c.date) " +
                        "FROM Position p " +
                        "JOIN p.pk.song s " +
                        "JOIN p.pk.chart c " +
                        "WHERE c.info.id = :ci " +
                        "AND lower(s.name) LIKE :q " +
                        "GROUP BY s.id, s.weeks, s.peak, s.artists, s.name " +
                        "ORDER BY MIN(c.date) ASC, s.id ASC"
        );
        query.setInteger("ci", chartInfoId);
        query.setString("q", "%" + qStr + "%");

        List<Object[]> rows = query.list();

        session.getTransaction().commit();
        session.close();

        return mapArtistSongRows(rows);
    }

    public static List<Song> getLongestSongs(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(
                "SELECT DISTINCT s " +
                        "FROM Position p " +
                        "JOIN p.pk.song s " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "ORDER BY s.weeks DESC, s.peak ASC");
        query.setInteger("ci", chartInfoId);
        query.setMaxResults(50);

        List<Song> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static List getLongestNo1Songs(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(
                "SELECT count(p.position) as cnt, " +
                        "s.id, s.peak, s.weeks, s.artists, s.name " +
                        "FROM Position p " +
                        "JOIN p.pk.song s " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "AND s.peak = 1 " +
                        "AND p.position = 1 " +
                        "GROUP BY s.id, s.peak, s.weeks, s.artists, s.name " +
                        "ORDER BY cnt DESC, s.weeks DESC");
        query.setInteger("ci", chartInfoId);
        query.setMaxResults(30);

        List list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static List getBiggestScoreSongs(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(
                "SELECT SUM(41 - p.position) as sumpos, " +
                        "s.id, s.peak, s.weeks, s.artists, s.name " +
                        "FROM Position p " +
                        "JOIN p.pk.song s " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "GROUP BY s.id, s.peak, s.weeks, s.artists, s.name " +
                        "ORDER BY sumpos DESC");
        query.setInteger("ci", chartInfoId);
        query.setMaxResults(50);

        List list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static List getBiggestScoreSongsByDate(int chartInfoId, String date1, String date2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String hql =
                "SELECT SUM(41 - p.position) as sumpos, " +
                        "s.id, " +
                        "MIN(p.position) as periodPeak, " +
                        "COUNT(p.position) as periodWeeks, " +
                        "s.artists, s.name " +
                        "FROM Position p " +
                        "JOIN p.pk.song s " +
                        "JOIN p.pk.chart c " +
                        "WHERE c.info.id = :ci " +
                        "AND c.date >= :date1 " +
                        (date2 != null && !date2.isEmpty() ? "AND c.date <= :date2 " : "") +
                        "GROUP BY s.id, s.artists, s.name " +
                        "ORDER BY sumpos DESC";

        Query query = session.createQuery(hql);
        query.setInteger("ci", chartInfoId);
        query.setParameter("date1", date1);

        if (date2 != null && !date2.isEmpty()) {
            query.setParameter("date2", date2);
        }

        query.setMaxResults(50);

        List list = query.list();
        session.getTransaction().commit();
        session.close();

        return list;
    }


    public static List<ArtistSongRow> getArtistSongRows(int chartInfoId, String artist) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String a = artist == null ? "" : artist.trim().toLowerCase();

        Query query = session.createQuery(
                "SELECT s, MIN(c.date) " +
                        "FROM Position p " +
                        "JOIN p.pk.song s " +
                        "JOIN p.pk.chart c " +
                        "WHERE c.info.id = :ci " +
                        "AND (" +
                        "   lower(trim(s.artists)) = :a " +
                        "   OR lower(s.artists) LIKE :aPrefix " +
                        "   OR lower(s.artists) LIKE :aSuffix " +
                        "   OR lower(s.artists) LIKE :aSuffixSp " +
                        "   OR lower(s.artists) LIKE :aMiddle1 " +
                        "   OR lower(s.artists) LIKE :aMiddle2 " +
                        ") " +
                        "GROUP BY s.id, s.weeks, s.peak, s.artists, s.name " +
                        "ORDER BY MIN(c.date) ASC, s.id ASC"
        );

        query.setInteger("ci", chartInfoId);
        query.setParameter("a", a);
        query.setParameter("aPrefix", a + ",%");
        query.setParameter("aSuffix", "%," + a);
        query.setParameter("aSuffixSp", "%, " + a);
        query.setParameter("aMiddle1", "%," + a + ",%");
        query.setParameter("aMiddle2", "%, " + a + ",%");

        List<Object[]> rows = query.list();

        session.getTransaction().commit();
        session.close();

        return mapArtistSongRows(rows);
    }

    public static List<String> getArtistsBySearch(int chartInfoId, String searchPhrase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String qStr = (searchPhrase == null) ? "" : searchPhrase.trim().toLowerCase();

        Query query = session.createQuery(
                "SELECT DISTINCT s.artists " +
                        "FROM Position p " +
                        "JOIN p.pk.song s " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "AND lower(s.artists) LIKE :q " +
                        "ORDER BY s.artists");
        query.setInteger("ci", chartInfoId);
        query.setParameter("q", "%" + qStr + "%");

        List<String> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static boolean existsInChartInfo(long songId, int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery(
                "SELECT 1 " +
                        "FROM Position p " +
                        "WHERE p.pk.song.id = :sid " +
                        "AND p.pk.chart.info.id = :ci");
        q.setLong("sid", songId);
        q.setInteger("ci", chartInfoId);
        q.setMaxResults(1);
        Object one = q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return one != null;
    }

    public static List<Object[]> getArtistRowsForSongStatsAllTime(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(
                "SELECT DISTINCT s.peak, s.artists " +
                        "FROM Position p " +
                        "JOIN p.pk.song s " +
                        "WHERE p.pk.chart.info.id = :ci");
        query.setInteger("ci", chartInfoId);

        List<Object[]> rows = query.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }

    public static List<String> getArtists(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(
                "SELECT DISTINCT s.artists " +
                        "FROM Position p " +
                        "JOIN p.pk.song s " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "ORDER BY s.artists");
        query.setInteger("ci", chartInfoId);

        List<String> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static long getLastId() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT MAX(id) " +
                "FROM Song c ");
        long result = (Long)query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static void update(Song result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(result);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Song " +
                "WHERE id = :id");
        query.setInteger("id",id);
        Song result = (Song) query.uniqueResult();
        session.delete(result);
        session.getTransaction().commit();
        session.close();
    }

    private static List<ArtistSongRow> mapArtistSongRows(List<Object[]> rows) {
        List<ArtistSongRow> result = new ArrayList<>();
        DateTimeFormatter outFmt = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

        for (Object[] row : rows) {
            Song song = (Song) row[0];
            String firstDateRaw = (String) row[1];

            String firstDateFormatted = "";
            String firstDateSortable = "";

            if (firstDateRaw != null && !firstDateRaw.isEmpty()) {
                firstDateSortable = firstDateRaw;
                try {
                    firstDateFormatted = LocalDate.parse(firstDateRaw).format(outFmt);
                } catch (Exception ignored) {
                    firstDateFormatted = firstDateRaw;
                }
            }

            result.add(new ArtistSongRow(song, firstDateFormatted, firstDateSortable));
        }

        return result;
    }
}

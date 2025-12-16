package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.model.Position;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class PositionDAOImpl {

    public static void save(Position result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(result);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Position> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Position p " +
                "LEFT JOIN FETCH p.pk.chart  " +
                "LEFT JOIN FETCH p.pk.song");
        List<Position> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static void update(Position result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(result);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Position " +
                "WHERE id = :id");
        query.setInteger("id",id);
        Position result = (Position) query.uniqueResult();
        session.delete(result);
        session.getTransaction().commit();
        session.close();
    }

    public static Position getPositionForSong(long idSong, long idChart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Position p " +
                "WHERE p.pk.song.id = :idSong " +
                "AND p.pk.chart.id = :idChart ");
        query.setParameter("idSong", idSong);
        query.setParameter("idChart", idChart);
        List<Position> results = query.list();
        session.getTransaction().commit();
        session.close();
        if (results.size() > 0 ) {
            return results.get(0);
        } else {
            return null;
        }
    }

    public static List<Position> getPositionsForSong(long idSong) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Position p " +
                "WHERE p.pk.song.id = :idSong " +
                "ORDER BY p.pk.chart.id ASC ");
        query.setParameter("idSong", idSong);
        List<Position> results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List<Position> getPositionsForSongByDate(long idSong, String date1, String date2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Position p " +
                "WHERE p.pk.song.id = :idSong " +
                "AND p.pk.chart.date >= :date1 AND p.pk.chart.date <= :date2 " +
                "ORDER BY p.pk.chart.id ASC ");
        query.setParameter("idSong", idSong);
        query.setParameter("date1", date1);
        query.setParameter("date2", date2);
        List<Position> results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List<String> getArtistsByDate(String date1, String date2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery( "SELECT DISTINCT p.pk.song.artists " +
                "FROM Position p " +
                "WHERE p.pk.chart.date >= :date1 " +
                (date2 != null && !date2.isEmpty() ? "AND p.pk.chart.date <= :date2 " : ""));
        query.setParameter("date1", date1);
        if (date2 != null && !date2.isEmpty()) {
            query.setParameter("date2", date2);
        }
        List<String> results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List getWOCforChart(long idChart, List<Long> songIds) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT p.pk.song.id, COUNT(*) " +
                "FROM Position p " +
                "WHERE p.pk.chart.id <= :idChart " +
                "AND p.pk.song.id in (:songIds) " +
                "GROUP BY p.pk.song.id");
        query.setParameter("songIds", songIds);
        query.setParameter("idChart", idChart);
        List results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List getPeaksForChart(long idChart, List<Long> songIds) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT p.pk.song.id, MIN(p.position) " +
                "FROM Position p " +
                "WHERE p.pk.chart.id <= :idChart " +
                "AND p.pk.song.id in (:songIds) " +
                "GROUP BY p.pk.song.id");
        query.setParameter("songIds", songIds);
        query.setParameter("idChart", idChart);
        List results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List<Object[]> getArtistRowsForStatsAllTime() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "SELECT p.position, p.pk.song.artists " +
                        "FROM Position p"
        );
        List<Object[]> rows = query.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }

    public static List<Object[]> getArtistRowsForStatsByDate(String date1, String date2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT p.position, p.pk.song.artists " +
                "FROM Position p " +
                "WHERE p.pk.chart.date >= :date1 " +
                (date2 != null && !date2.isEmpty() ? "AND p.pk.chart.date <= :date2 " : ""));
        query.setParameter("date1", date1);
        if (date2 != null && !date2.isEmpty())
            query.setParameter("date2", date2);
        List<Object[]> rows = query.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }

    public static List<Position> getNumberOneDebuts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "FROM Position p " +
                "WHERE p.position = 1 " +
                "AND p.lastWeek is null " +
                "AND NOT EXISTS ( " +
                        "   SELECT 1 " +
                        "   FROM Position p2 " +
                        "   WHERE p2.pk.song.id = p.pk.song.id " +
                        "   AND p2.pk.chart.id < p.pk.chart.id " +
                        ") " +
                "ORDER BY p.pk.chart.date DESC");
        List<Position> rows = query.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }

    public static List<Object[]> getBiggestJumpsUp() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery(
                "SELECT (p.lastWeek - p.position) as jump, p, p.pk.chart " +
                        "FROM Position p " +
                        "WHERE p.lastWeek is not null " +
                        "AND p.lastWeek > 0 " +
                        "AND p.lastWeek > p.position " +
                        "ORDER BY jump DESC, p.pk.chart.id DESC"
        );
        q.setMaxResults(50);
        List<Object[]> rows = q.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }

    public static List<Object[]> getBiggestDrops() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query qDown = session.createQuery(
                "SELECT (p.position - p.lastWeek) as drop, p.pk.chart, " +
                        "p.pk.song, p.lastWeek, p.position, false " +
                        "FROM Position p " +
                        "WHERE p.lastWeek is not null " +
                        "AND p.lastWeek > 0 " +
                        "AND p.position > p.lastWeek"
        );

        Query qOut = session.createQuery(
                "SELECT (41 - prev.position) as drop, prev.pk.chart, prev.pk.song, " +
                        "prev.position, 41, true " +
                        "FROM Position prev, Chart c " +
                        "WHERE c.id = prev.pk.chart.id + 1 " +
                        "  AND prev.position is not null " +
                        "  AND prev.position > 0 " +
                        "  AND NOT EXISTS (" +
                        "      SELECT 1 FROM Position cur " +
                        "      WHERE cur.pk.chart.id = c.id " +
                        "        AND cur.pk.song.id = prev.pk.song.id" +
                        "  )"
        );

        List<Object[]> rows = new ArrayList<>();
        rows.addAll(qDown.list());
        rows.addAll(qOut.list());

        session.getTransaction().commit();
        session.close();

        rows.sort((a, b) -> Long.compare(((Number)b[0]).longValue(), ((Number)a[0]).longValue()));
        if (rows.size() > 50) rows.subList(50, rows.size()).clear();

        return rows;
    }

    public static List<Object[]> getSongPositionsRowsAllTime() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery(
                "SELECT p.pk.song.id, p.pk.chart.id, p.position, p.pk.song.artists, p.pk.song.name " +
                        "FROM Position p " +
                        "ORDER BY p.pk.song.id ASC, p.pk.chart.id ASC");
        List<Object[]> rows = q.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }

    public static List<Object[]> getLongestWaysToTop10() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT (MIN(t10.pk.chart.id) - MIN(p.pk.chart.id) + 1), " +
                        "MIN(t10.pk.chart), " +
                        "p.pk.song, " +
                        "MIN(p.pk.chart.id), " +
                        "MIN(t10.pk.chart.id) " +
                        "FROM Position p, Position t10 " +
                        "WHERE p.pk.song.id = t10.pk.song.id " +
                        "AND t10.position <= 10 " +
                        "GROUP BY p.pk.song " +
                        "HAVING MIN(t10.pk.chart.id) > MIN(p.pk.chart.id) " +
                        "ORDER BY (MIN(t10.pk.chart.id) - MIN(p.pk.chart.id)) DESC");
        query.setMaxResults(50);
        List<Object[]> rows = query.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }
}

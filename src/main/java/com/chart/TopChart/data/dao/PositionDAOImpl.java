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

    public static List<Position> getAll(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "FROM Position p " +
                        "LEFT JOIN FETCH p.pk.chart c " +
                        "LEFT JOIN FETCH p.pk.song s " +
                        "WHERE c.info.id = :ci");
        query.setInteger("ci", chartInfoId);
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

    public static Position getPositionForSong(int chartInfoId, long idSong, int issueNumber) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "FROM Position p " +
                        "WHERE p.pk.song.id = :idSong " +
                        "AND p.pk.chart.issueNumber = :issueNumber " +
                        "AND p.pk.chart.info.id = :ci");
        query.setParameter("idSong", idSong);
        query.setParameter("issueNumber", issueNumber);
        query.setInteger("ci", chartInfoId);
        Position result = (Position) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static List<Position> getPositionsForSong(int chartInfoId, long idSong) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "FROM Position p " +
                        "WHERE p.pk.song.id = :idSong " +
                        "AND p.pk.chart.info.id = :ci " +
                        "ORDER BY p.pk.chart.issueNumber ASC ");
        query.setParameter("idSong", idSong);
        query.setInteger("ci", chartInfoId);
        List<Position> results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }
    public static List<Position> getPositionsForSongByDate(int chartInfoId, long idSong, String date1, String date2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "FROM Position p " +
                        "WHERE p.pk.song.id = :idSong " +
                        "AND p.pk.chart.info.id = :ci " +
                        "AND p.pk.chart.date >= :date1 AND p.pk.chart.date <= :date2 " +
                        "ORDER BY p.pk.chart.issueNumber ASC ");
        query.setParameter("idSong", idSong);
        query.setInteger("ci", chartInfoId);
        query.setParameter("date1", date1);
        query.setParameter("date2", date2);
        List<Position> results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List<String> getArtistsByDate(int chartInfoId, String date1, String date2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql =
                "SELECT DISTINCT p.pk.song.artists " +
                        "FROM Position p " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "AND p.pk.chart.date >= :date1 " +
                        (date2 != null && !date2.isEmpty() ? "AND p.pk.chart.date <= :date2 " : "");
        Query query = session.createQuery(hql);
        query.setInteger("ci", chartInfoId);
        query.setParameter("date1", date1);
        if (date2 != null && !date2.isEmpty()) {
            query.setParameter("date2", date2);
        }
        List<String> results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List getWOCforChart(int chartInfoId, int issueNumber, List<Long> songIds) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "SELECT p.pk.song.id, COUNT(*) " +
                        "FROM Position p " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "AND p.pk.chart.issueNumber <= :issueNumber " +
                        "AND p.pk.song.id in (:songIds) " +
                        "GROUP BY p.pk.song.id");
        query.setInteger("ci", chartInfoId);
        query.setParameter("songIds", songIds);
        query.setParameter("issueNumber", issueNumber);
        List results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List getPeaksForChart(int chartInfoId, int issueNumber, List<Long> songIds) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "SELECT p.pk.song.id, MIN(p.position) " +
                        "FROM Position p " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "AND p.pk.chart.issueNumber <= :issueNumber " +
                        "AND p.pk.song.id in (:songIds) " +
                        "GROUP BY p.pk.song.id");
        query.setInteger("ci", chartInfoId);
        query.setParameter("songIds", songIds);
        query.setParameter("issueNumber", issueNumber);
        List results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List<Object[]> getArtistRowsForStatsAllTime(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "SELECT p.position, p.pk.song.artists " +
                        "FROM Position p " +
                        "WHERE p.pk.chart.info.id = :ci");
        query.setInteger("ci", chartInfoId);
        List<Object[]> rows = query.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }

    public static List<Object[]> getArtistRowsForStatsByDate(int chartInfoId, String date1, String date2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql =
                "SELECT p.position, p.pk.song.artists " +
                        "FROM Position p " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "AND p.pk.chart.date >= :date1 " +
                        (date2 != null && !date2.isEmpty() ? "AND p.pk.chart.date <= :date2 " : "");
        Query query = session.createQuery(hql);
        query.setInteger("ci", chartInfoId);
        query.setParameter("date1", date1);
        if (date2 != null && !date2.isEmpty())
            query.setParameter("date2", date2);
        List<Object[]> rows = query.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }

    public static List<Position> getNumberOneDebuts(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "FROM Position p " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "AND p.position = 1 " +
                        "AND p.lastWeek is null " +
                        "AND NOT EXISTS ( " +
                        "   SELECT 1 " +
                        "   FROM Position p2 " +
                        "   WHERE p2.pk.song.id = p.pk.song.id " +
                        "   AND p2.pk.chart.issueNumber < p.pk.chart.issueNumber " +
                        "   AND p2.pk.chart.info.id = :ci " +
                        ") " +
                        "ORDER BY p.pk.chart.date DESC");
        query.setInteger("ci", chartInfoId);
        List<Position> rows = query.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }

    public static List<Object[]> getBiggestJumpsUp(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery(
                "SELECT (p.lastWeek - p.position) as jump, p, p.pk.chart " +
                        "FROM Position p " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "AND p.lastWeek is not null " +
                        "AND p.lastWeek > 0 " +
                        "AND p.lastWeek > p.position " +
                        "ORDER BY jump DESC, p.pk.chart.issueNumber DESC");
        q.setInteger("ci", chartInfoId);
        q.setMaxResults(50);
        List<Object[]> rows = q.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }

    public static List<Object[]> getBiggestDrops(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query qDown = session.createQuery(
                "SELECT (p.position - p.lastWeek) as drop, p.pk.chart, " +
                        "p.pk.song, p.lastWeek, p.position, false " +
                        "FROM Position p " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "AND p.lastWeek is not null " +
                        "AND p.lastWeek > 0 " +
                        "AND p.position > p.lastWeek");
        qDown.setInteger("ci", chartInfoId);

        Query qOut = session.createQuery(
                "SELECT (41 - prev.position) as drop, prev.pk.chart, prev.pk.song, " +
                        "prev.position, 41, true " +
                        "FROM Position prev, com.chart.TopChart.data.model.Chart c " +
                        "WHERE c.info.id = :ci " +
                        "  AND prev.pk.chart.info.id = :ci " +
                        "  AND c.issueNumber = prev.pk.chart.issueNumber + 1 " +
                        "  AND prev.position is not null " +
                        "  AND prev.position > 0 " +
                        "  AND NOT EXISTS (" +
                        "      SELECT 1 FROM Position cur " +
                        "      WHERE cur.pk.chart.issueNumber = c.issueNumber " +
                        "        AND cur.pk.song.id = prev.pk.song.id" +
                        "        AND cur.pk.chart.info.id = :ci " +
                        "  )");
        qOut.setInteger("ci", chartInfoId);

        List<Object[]> rows = new ArrayList<>();
        rows.addAll(qDown.list());
        rows.addAll(qOut.list());

        session.getTransaction().commit();
        session.close();

        rows.sort((a, b) -> Long.compare(((Number)b[0]).longValue(), ((Number)a[0]).longValue()));
        if (rows.size() > 50) rows.subList(50, rows.size()).clear();

        return rows;
    }

    public static List<Object[]> getSongPositionsRowsAllTime(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery(
                "SELECT p.pk.song.id, p.pk.chart.issueNumber, p.position, p.pk.song.artists, p.pk.song.name " +
                        "FROM Position p " +
                        "WHERE p.pk.chart.info.id = :ci " +
                        "ORDER BY p.pk.song.id ASC, p.pk.chart.issueNumber ASC");
        q.setInteger("ci", chartInfoId);
        List<Object[]> rows = q.list();
        session.getTransaction().commit();
        session.close();
        return rows;
    }
}
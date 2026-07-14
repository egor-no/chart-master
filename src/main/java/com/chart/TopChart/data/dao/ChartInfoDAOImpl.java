package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.dto.HomeChartInfoRow;
import com.chart.TopChart.data.dto.HomeUpdateRow;
import com.chart.TopChart.data.model.ChartInfo;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChartInfoDAOImpl {

    public static int save(ChartInfo result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        int id = (Integer) session.save(result);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    public static void deleteWithUnusedSongs(int chartInfoId, int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        try {
            ChartInfo ci = (ChartInfo) session.createQuery(
                    "FROM ChartInfo ci " +
                            "LEFT JOIN FETCH ci.owner " +
                            "WHERE ci.id = :id"
            )
                    .setInteger("id", chartInfoId)
                    .uniqueResult();

            if (ci == null || ci.getOwner() == null || ci.getOwner().getId() != userId) {
                throw new RuntimeException("ChartInfo not found or not owned by user");
            }

            List<Long> songIds = session.createQuery(
                    "SELECT DISTINCT p.pk.song.id " +
                            "FROM Position p " +
                            "WHERE p.pk.chart.info.id = :chartInfoId"
            )
                    .setInteger("chartInfoId", chartInfoId)
                    .list();

            session.delete(ci);
            session.flush();

            if (songIds != null && !songIds.isEmpty()) {
                session.createQuery(
                        "DELETE FROM Song s " +
                                "WHERE s.id IN (:songIds) " +
                                "AND NOT EXISTS (" +
                                "   SELECT 1 FROM Position p " +
                                "   WHERE p.pk.song.id = s.id" +
                                ")"
                )
                        .setParameterList("songIds", songIds)
                        .executeUpdate();
            }

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static List<ChartInfo> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM ChartInfo " +
                "ORDER BY id DESC");
        List<ChartInfo> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static List<ChartInfo> getByUser(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM ChartInfo " +
                "WHERE owner.id = :userId " +
                "ORDER BY id DESC");
        query.setInteger("userId", userId);
        List<ChartInfo> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static ChartInfo getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM ChartInfo ci " +
                "LEFT JOIN FETCH ci.owner " +
                "WHERE ci.id = :id");
        query.setInteger("id", id);
        ChartInfo result = (ChartInfo) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static boolean isOwner(int chartInfoId, int userId) {
        try (Session session = HibernateUtil
                .getSessionFactory()
                .openSession()) {

            Long count = session.createQuery(
                    "select count(ci.id) " +
                            "from ChartInfo ci " +
                            "where ci.id = :chartInfoId " +
                            "and ci.owner.id = :userId",
                    Long.class
            )
                    .setParameter("chartInfoId", chartInfoId)
                    .setParameter("userId", userId)
                    .uniqueResult();

            return count != null && count > 0;
        }
    }

    public static List<HomeChartInfoRow> getAllForHome() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(
                "SELECT new com.chart.TopChart.data.dto.HomeChartInfoRow(" +
                        "ci.id, ci.title, ci.description, ci.size, " +
                        "o.nickname, o.id, count(c.id)" +
                        ") " +
                        "FROM ChartInfo ci " +
                        "JOIN ci.owner o " +
                        "LEFT JOIN Chart c ON c.info.id = ci.id " +
                        "GROUP BY ci.id, ci.title, ci.description, ci.size, o.nickname, o.id " +
                        "ORDER BY o.nickname, ci.title"
        );

        List<HomeChartInfoRow> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @SuppressWarnings("unchecked")
    public static List<HomeUpdateRow> getLatestChartInfosForUpdates(int limit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query q = session.createQuery(
                "SELECT ci.id, ci.title, o.id, o.nickname, ci.createdAt " +
                        "FROM ChartInfo ci " +
                        "JOIN ci.owner o " +
                        "ORDER BY ci.createdAt DESC"
        );
        q.setMaxResults(limit);

        List<Object[]> rows = q.list();

        session.getTransaction().commit();
        session.close();

        List<HomeUpdateRow> out = new ArrayList<>();
        for (Object[] r : rows) {
            Integer ciId = (Integer) r[0];
            String title = (String) r[1];
            Integer ownerId = (Integer) r[2];
            String ownerNick = (String) r[3];
            LocalDateTime createdAt = (LocalDateTime) r[4];

            out.add(HomeUpdateRow.chartInfo(ciId, title, ownerId, ownerNick, createdAt));
        }
        return out;
    }

    public static long getTotalChartInfosCount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT count(ci.id) FROM ChartInfo ci");
        Long val = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return val == null ? 0 : val;
    }

    public static void update(ChartInfo result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(result);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM ChartInfo " +
                "WHERE id = :id");
        query.setInteger("id",id);
        ChartInfo result = (ChartInfo) query.uniqueResult();
        session.delete(result);
        session.getTransaction().commit();
        session.close();
    }
}

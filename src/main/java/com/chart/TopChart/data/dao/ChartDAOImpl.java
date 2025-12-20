package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.dto.ChartBasic;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.dto.HomeLatestChartRow;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartDAOImpl {

    public static long save(Chart result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        long id = (Long)session.save(result);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    public static List<Chart> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Chart " +
                "ORDER BY id ");
        List<Chart> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static List<Chart> getAll(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "FROM Chart c " +
                        "WHERE c.info.id = :ci " +
                        "ORDER BY c.id");
        query.setInteger("ci", chartInfoId);
        List<Chart> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static Chart getById(int chartInfoId, long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT DISTINCT c FROM Chart c " +
                "LEFT JOIN FETCH c.positions " +
                "LEFT JOIN FETCH c.info ci " +
                "LEFT JOIN FETCH ci.owner " +
                "WHERE c.id = :id " +
                "AND c.info.id = :ciid ");
        query.setLong("id", id);
        query.setInteger("ciid", chartInfoId);
        Chart result = (Chart) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static long getLastByDate(String sDate, int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "SELECT MAX(c.id) " +
                        "FROM Chart c " +
                        "WHERE c.info.id = :ci " +
                        "AND c.date <= :date"
        );
        query.setInteger("ci", chartInfoId);
        query.setParameter("date", sDate);
        Long result = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result == null ? 0L : result;
    }

    public static Long getLastId(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("SELECT " +
                "MAX(c.id) FROM Chart c " +
                "WHERE c.info.id = :ci");
        q.setInteger("ci", chartInfoId);
        Long res = (Long) q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return res;
    }

    public static Long getPrevId(int chartInfoId, long currentChartId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery(
                "SELECT MAX(c.id) " +
                        "FROM Chart c " +
                        "WHERE c.info.id = :ci " +
                        "AND c.id < :cur");
        q.setInteger("ci", chartInfoId);
        q.setLong("cur", currentChartId);
        Long res = (Long) q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return res;
    }

    public static List<HomeLatestChartRow> getLatestForHome(int limit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(
                "SELECT new com.chart.TopChart.data.dto.HomeLatestChartRow(" +
                        "ci.id, ci.title, c.id, c.date, o.nickname" +
                        ") " +
                        "FROM Chart c " +
                        "JOIN c.info ci " +
                        "JOIN ci.owner o " +
                        "ORDER BY c.id DESC");
        query.setMaxResults(limit);

        List<HomeLatestChartRow> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static long getTotalChartsCount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT count(c.id) " +
                "FROM Chart c");
        Long val = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return val == null ? 0 : val;
    }

    public static Map<Integer, List<ChartBasic>> getLastChartsByChartInfoIds(
            List<Integer> chartInfoIds, int limitPerChart) {

        Map<Integer, List<ChartBasic>> result = new HashMap<>();
        if (chartInfoIds == null || chartInfoIds.isEmpty() || limitPerChart <= 0) {
            return result;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query q = session.createQuery(
                "SELECT c.info.id, c.id, c.date " +
                        "FROM Chart c " +
                        "WHERE c.info.id IN (:ids) " +
                        "ORDER BY c.info.id ASC, c.id DESC"
        );
        q.setParameterList("ids", chartInfoIds);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.list();

        session.getTransaction().commit();
        session.close();

        for (Object[] row : rows) {
            Integer ciId = (Integer) row[0];
            Long chartId = (Long) row[1];
            String date = (String) row[2];

            List<ChartBasic> list =
                    result.computeIfAbsent(ciId, k -> new ArrayList<>());

            if (list.size() < limitPerChart) {
                list.add(new ChartBasic(chartId, date));
            }
        }

        return result;
    }

    public static void update(Chart result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(result);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(int chartInfoId, long id) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "FROM Chart c " +
                            "WHERE c.id = :id AND c.info.id = :ci");
            query.setLong("id", id);
            query.setInteger("ci", chartInfoId);
            Chart result = (Chart) query.uniqueResult();
            session.delete(result);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw new Exception(ex);
        } finally {
            session.close();
        }
    }

}

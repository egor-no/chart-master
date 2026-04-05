package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.dto.ChartBasic;
import com.chart.TopChart.data.dto.HomeUpdateRow;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.dto.HomeLatestChartRow;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                        "ORDER BY c.issueNumber");
        query.setInteger("ci", chartInfoId);
        List<Chart> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static Chart getById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT DISTINCT c FROM Chart c " +
                "LEFT JOIN FETCH c.positions " +
                "LEFT JOIN FETCH c.info ci " +
                "LEFT JOIN FETCH ci.owner " +
                "WHERE c.id = :id ");
        query.setLong("id", id);
        Chart result = (Chart) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static Chart getByIssueNumber(int chartInfoId, int issueNumber) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT DISTINCT c FROM Chart c " +
                "LEFT JOIN FETCH c.positions " +
                "LEFT JOIN FETCH c.info ci " +
                "LEFT JOIN FETCH ci.owner " +
                "WHERE c.issueNumber = :issueNumber " +
                "AND c.info.id = :ciid ");
        query.setInteger("issueNumber", issueNumber);
        query.setInteger("ciid", chartInfoId);
        Chart result = (Chart) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static int getLastIssueNumberByDate(String sDate, int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "SELECT MAX(c.issueNumber) " +
                        "FROM Chart c " +
                        "WHERE c.info.id = :ci " +
                        "AND c.date <= :date"
        );
        query.setInteger("ci", chartInfoId);
        query.setParameter("date", sDate);
        Integer result = (Integer) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result == null ? 0 : result;
    }

    public static Long getLastId() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("SELECT " +
                "MAX(c.id) FROM Chart c ");
        Long res = (Long) q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return res;
    }

    public static Integer getLastIssueNumber(int chartInfoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("SELECT " +
                "MAX(c.issueNumber) FROM Chart c " +
                "WHERE c.info.id = :ci "
        );
        q.setInteger("ci", chartInfoId);
        Integer res = (Integer) q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return res;
    }

    public static Integer getPrevIssueNumber(int chartInfoId, int issueNumber) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery(
                "SELECT MAX(c.issueNumber) " +
                        "FROM Chart c " +
                        "WHERE c.info.id = :ci " +
                        "AND c.issueNumber < :cur");
        q.setInteger("ci", chartInfoId);
        q.setInteger("cur", issueNumber);
        Integer res = (Integer) q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return res;
    }

    @SuppressWarnings("unchecked")
    public static List<HomeUpdateRow> getLatestIssuesForUpdates(int limit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query q = session.createQuery(
                "SELECT ci.id, ci.title, o.nickname, c.issueNumber, c.date " +
                        "FROM Chart c " +
                        "JOIN c.info ci " +
                        "JOIN ci.owner o " +
                        "ORDER BY c.date DESC, c.id DESC"
        );
        q.setMaxResults(limit);

        List<Object[]> rows = q.list();

        session.getTransaction().commit();
        session.close();

        List<HomeUpdateRow> out = new ArrayList<>();
        for (Object[] r : rows) {
            Integer ciId = (Integer) r[0];
            String ciTitle = (String) r[1];
            String ownerNick = (String) r[2];
            Integer issueNumber = (Integer) r[3];
            String chartDate = (String) r[4];

            LocalDateTime sortTime;
            try {
                sortTime = LocalDate.parse(chartDate).atStartOfDay();
            } catch (Exception ex) {
                sortTime = LocalDateTime.MIN;
            }

            out.add(HomeUpdateRow.issue(ciId, ciTitle, ownerNick, issueNumber, chartDate, sortTime));
        }
        return out;
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
                "SELECT c.info.id, c.issueNumber, c.date " +
                        "FROM Chart c " +
                        "WHERE c.info.id IN (:ids) " +
                        "ORDER BY c.info.id ASC, c.issueNumber DESC"
        );
        q.setParameterList("ids", chartInfoIds);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.list();

        session.getTransaction().commit();
        session.close();

        for (Object[] row : rows) {
            Integer ciId = (Integer) row[0];
            Integer issueNumber = (Integer) row[1];
            String date = (String) row[2];

            List<ChartBasic> list =
                    result.computeIfAbsent(ciId, k -> new ArrayList<>());

            if (list.size() < limitPerChart) {
                list.add(new ChartBasic(issueNumber, date));
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

    public static void delete(int chartInfoId, int issueNumber) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery(
                    "FROM Chart c " +
                            "WHERE c.issueNumber = :issueNumber AND c.info.id = :ci");
            query.setInteger("issueNumber", issueNumber);
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

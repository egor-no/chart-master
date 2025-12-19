package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.dto.HomeChartInfoRow;
import com.chart.TopChart.data.model.ChartInfo;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

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
        Query query = session.createQuery("FROM ChartInfo " +
                "WHERE id = :id");
        query.setInteger("id", id);
        ChartInfo result = (ChartInfo) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
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

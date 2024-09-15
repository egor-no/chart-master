package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.model.Chart;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

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

    public static Chart getById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Chart c " +
                "LEFT JOIN FETCH c.positions " +
                "WHERE c.id = :id");
        query.setLong("id", id);
        Chart result = (Chart) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static long getLastId() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT MAX(id) " +
                "FROM Chart c ");
        long result = (Long)query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static void update(Chart result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(result);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(long id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Chart " +
                "WHERE id = :id");
        query.setLong("id",id);
        Chart result = (Chart) query.uniqueResult();
        session.delete(result);
        session.getTransaction().commit();
        session.close();
    }

}

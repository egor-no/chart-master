package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.model.Chart;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class ChartDAOImpl {

    public static void save(Chart result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(result);
        session.getTransaction().commit();
        session.close();
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

    public static Chart getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Chart c " +
                "LEFT JOIN FETCH c.positions " +
                "WHERE c.id = :id");
        query.setInteger("id", id);
        Chart result = (Chart) query.uniqueResult();
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

    public static void delete(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM Chart " +
                "WHERE id = :id");
        query.setInteger("id",id);
        Chart result = (Chart) query.uniqueResult();
        session.delete(result);
        session.getTransaction().commit();
        session.close();
    }

}

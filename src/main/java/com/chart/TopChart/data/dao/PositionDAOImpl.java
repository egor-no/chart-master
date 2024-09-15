package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.model.Position;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

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

    public static long getWocByChart(long idChart, long idSong) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT COUNT(*) " +
                "FROM Position p " +
                "WHERE p.pk.chart.id <= :idChart " +
                "AND p.pk.song.id = :idSong");
        query.setParameter("idSong", idSong);
        query.setParameter("idChart", idChart);
        long result = (Long)query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
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
}

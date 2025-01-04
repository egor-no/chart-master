package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.model.Song;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class SongDAOImpl {

    public static long save(Song result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        long id = (Long) session.save(result);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    public static List<Song> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Song " +
                "ORDER BY id DESC");
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

}

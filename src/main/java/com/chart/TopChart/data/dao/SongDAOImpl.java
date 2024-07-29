package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.model.Song;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class SongDAOImpl {

    public static void save(Song result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(result);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Song> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Song " +
                "ORDER BY id ");
        List<Song> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static Song getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Song " +
                "WHERE id = :id");
        query.setInteger("id", id);
        Song result = (Song) query.uniqueResult();
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

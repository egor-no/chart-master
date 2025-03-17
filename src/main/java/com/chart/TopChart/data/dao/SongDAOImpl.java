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

    public static List<Song> getBySearchPhrase(String searchPhrase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Song s " +
                "WHERE s.name LIKE :searchPhrase ");
        query.setParameter("searchPhrase", "%" + searchPhrase + "%");
        List<Song> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static List<Song> getLongestSongs() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Song " +
                "ORDER BY weeks DESC ");
        query.setMaxResults(50);
        List<Song> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static List getLongestNo1Songs() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT count(p.position) as cnt, " +
                "s.id, s.peak, s.weeks, s.artists, s.name " +
                "FROM Song s " +
                "LEFT JOIN Position p ON p.pk.song.id = s.id " +
                "WHERE s.peak = 1 " +
                "AND p.position = 1 " +
                "GROUP BY s.id, s.peak, s.weeks, s.artists, s.name " +
                "ORDER BY cnt DESC");
        query.setMaxResults(30);
        List list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static List<Song> getByArtist(String artist) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Song s " +
                "WHERE s.artists LIKE :artist ");
        query.setParameter("artist", "%" + artist + "%");
        List<Song> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static List<String> getArtistsBySearch(String searchPhrase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT DISTINCT s.artists " +
                "FROM Song s " +
                "WHERE s.artists LIKE :searchPhrase ");
        query.setParameter("searchPhrase", "%" + searchPhrase + "%");
        List<String> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static long getArtistTopStat(String artist, int top) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT COUNT(*)" +
                "FROM Song s " +
                "WHERE s.artists LIKE :artist " +
                "AND s.peak <= :top ");
        query.setParameter("artist", "%" + artist + "%");
        query.setParameter("top", top);
        Long count = (Long)query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count;
    }

    public static List<String> getArtists() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT DISTINCT s.artists " +
                "FROM Song s " +
                "ORDER BY s.artists ");
        List<String> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
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

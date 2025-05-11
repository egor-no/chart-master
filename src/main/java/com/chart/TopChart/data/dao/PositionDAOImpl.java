package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.Position;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.ArrayList;
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

    public static Position getPositionForSong(long idSong, long idChart) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Position p " +
                "WHERE p.pk.song.id = :idSong " +
                "AND p.pk.chart.id = :idChart ");
        query.setParameter("idSong", idSong);
        query.setParameter("idChart", idChart);
        List<Position> results = query.list();
        session.getTransaction().commit();
        session.close();
        if (results.size() > 0 ) {
            return results.get(0);
        } else {
            return null;
        }
    }

    public static List<Position> getPositionsForSong(long idSong) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Position p " +
                "WHERE p.pk.song.id = :idSong " +
                "ORDER BY p.pk.chart.id ASC ");
        query.setParameter("idSong", idSong);
        List<Position> results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List<Position> getPositionsForSongByDate(long idSong, String date1, String date2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Position p " +
                "WHERE p.pk.song.id = :idSong " +
                "AND p.pk.chart.date >= :date1 AND p.pk.chart.date <= :date2 " +
                "ORDER BY p.pk.chart.id ASC ");
        query.setParameter("idSong", idSong);
        query.setParameter("date1", date1);
        query.setParameter("date2", date2);
        List<Position> results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List getWOCforChart(long idChart, List<Long> songIds) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT p.pk.song.id, COUNT(*) " +
                "FROM Position p " +
                "WHERE p.pk.chart.id <= :idChart " +
                "AND p.pk.song.id in (:songIds) " +
                "GROUP BY p.pk.song.id");
        query.setParameter("songIds", songIds);
        query.setParameter("idChart", idChart);
        List results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static List getPeaksForChart(long idChart, List<Long> songIds) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT p.pk.song.id, MIN(p.position) " +
                "FROM Position p " +
                "WHERE p.pk.chart.id <= :idChart " +
                "AND p.pk.song.id in (:songIds) " +
                "GROUP BY p.pk.song.id");
        query.setParameter("songIds", songIds);
        query.setParameter("idChart", idChart);
        List results = query.list();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public static long getArtistTopLength(String artist, int top) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT COUNT(*)" +
                "FROM Position p " +
                "WHERE p.pk.song.artists LIKE :artist " +
                "AND p.position <= :top ");
        query.setParameter("artist", "%" + artist + "%");
        query.setParameter("top", top);
        Long count = (Long)query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count;
    }
}

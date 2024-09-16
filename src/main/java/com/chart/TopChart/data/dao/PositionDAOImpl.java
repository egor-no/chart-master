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

    public static List<Long> getWOCforChart(Chart chart) {
        List<Long> woc = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        for (int i = 0; i < chart.getPositions().size(); i++) {
            Query query = session.createQuery("SELECT COUNT(*) " +
                    "FROM Position p " +
                    "WHERE p.pk.chart.id <= :idChart " +
                    "AND p.pk.song.id = :idSong");
            query.setParameter("idSong", chart.getPositions().get(i).getPk().getSong().getId());
            query.setParameter("idChart", chart.getId());
            long result = (Long) query.uniqueResult();
            woc.add(result);
        }
        session.getTransaction().commit();
        session.close();
        return woc;
    }

    public static List<Integer> getPeaksForChart(Chart chart) {
        List<Integer> peaks = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        for (int i = 0; i < chart.getPositions().size(); i++) {
            Query query = session.createQuery("SELECT MIN(p.position) " +
                    "FROM Position p " +
                    "WHERE p.pk.chart.id <= :idChart " +
                    "AND p.pk.song.id = :idSong");
            query.setParameter("idSong", chart.getPositions().get(i).getPk().getSong().getId());
            query.setParameter("idChart", chart.getId());
            int result = (Integer) query.uniqueResult();
            peaks.add(result);
        }
        session.getTransaction().commit();
        session.close();
        return peaks;
    }
}

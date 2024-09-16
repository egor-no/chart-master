package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.model.Chart;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class PositionService {

    public static List<Long> getWOCforChart(Chart chart) {
        long time1 = System.nanoTime();
        List<Long> woc = new ArrayList<>();
        for (int i = 0; i < chart.getPositions().size(); i++) {
            woc.add(PositionDAOImpl.getWocByChart(chart.getId(), chart.getPositions().get(i).getPk().getSong().getId()));
        }
        long time2 = System.nanoTime();
        System.out.println("TIME " + (time2-time1));
        return woc;
    }

    public static List<Integer> getPeaksForChart(Chart chart) {
        long time1 = System.nanoTime();
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
        long time2 = System.nanoTime();
        System.out.println("TIME ЗУФЛ " + (time2-time1));
        return peaks;
    }
}

package com.chart.TopChart.data.dao;

import com.chart.TopChart.data.dto.HomeUserRow;
import com.chart.TopChart.data.model.ChartInfo;
import com.chart.TopChart.data.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class UserDAOImpl {

    public static int save(User result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        int id = (Integer) session.save(result);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    public static List<User> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User " +
                "ORDER BY id DESC");
        List<User> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static User getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User u " +
                "LEFT JOIN FETCH u.charts " +
                "WHERE u.id = :id");
        query.setInteger("id", id);
        User result = (User) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static User getByLogin(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User " +
                "WHERE login LIKE :login");
        query.setString("login", login);
        User result = (User) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static List<HomeUserRow> getAllWithChartsCount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(
                "SELECT new com.chart.TopChart.data.dto.HomeUserRow(" +
                        "u.id, u.login, u.nickname, count(ci.id) as amount " +
                        ") " +
                        "FROM User u " +
                        "LEFT JOIN u.charts ci " +
                        "GROUP BY u.id, u.login, u.nickname " +
                        "ORDER BY amount DESC "
        );

        List<HomeUserRow> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public static long getTotalUsersCount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT count(u.id) FROM User u");
        Long val = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return val == null ? 0 : val;
    }

    public static void update(User result) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(result);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query=session.createQuery("FROM User " +
                "WHERE id = :id");
        query.setInteger("id",id);
        User result = (User) query.uniqueResult();
        session.delete(result);
        session.getTransaction().commit();
        session.close();
    }
}

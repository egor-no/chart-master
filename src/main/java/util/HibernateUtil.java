package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.net.URI;


public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();

    public HibernateUtil() {
    }

    private static SessionFactory buildSessionFactory() {
        try {
            return (new Configuration()).configure("sqlserverMain.cfg.xml").buildSessionFactory();
        } catch (Exception var1) {
            throw new ExceptionInInitializerError(var1);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}


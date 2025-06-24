package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration config = new Configuration().configure("sqlserverMain.cfg.xml");

            // Поддержка Railway
            String rawUrl = System.getenv("DATABASE_URL");
            String user = System.getenv("MYSQLUSER");
            String password = System.getenv("MYSQLPASSWORD");

            if (rawUrl != null && rawUrl.startsWith("mysql://")) {
                rawUrl = rawUrl.replace("mysql://", "jdbc:mysql://");
            }

            if (rawUrl != null) {
                config.setProperty("hibernate.connection.url", rawUrl);
            }
            if (user != null) {
                config.setProperty("hibernate.connection.username", user);
            }
            if (password != null) {
                config.setProperty("hibernate.connection.password", password);
            }

            return config.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
            return sessionFactory;
        }

}

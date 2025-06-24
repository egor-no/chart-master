package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.net.URI;

public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration config = new Configuration().configure("sqlserverMain.cfg.xml");

            String rawUrl = System.getenv("DATABASE_URL");

            if (rawUrl != null && rawUrl.startsWith("mysql://")) {
                // Преобразуем в jdbc:mysql:// и парсим URI
                URI dbUri = new URI(rawUrl.replace("mysql://", "http://"));

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String jdbcUrl = "jdbc:mysql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath()
                        + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

                config.setProperty("hibernate.connection.url", jdbcUrl);
                config.setProperty("hibernate.connection.username", username);
                config.setProperty("hibernate.connection.password", password);
            } else {
                System.err.println("⚠️ DATABASE_URL is not set or invalid");
            }

            return config.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace(); // важно для Railway логов
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
            return sessionFactory;
        }

}

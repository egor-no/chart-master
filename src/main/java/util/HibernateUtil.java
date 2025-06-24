package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.net.URI;

public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        private static SessionFactory sessionFactory = buildSessionFactory();

        private static SessionFactory buildSessionFactory() {
            try {
                Configuration config = new Configuration().configure("sqlserverMain.cfg.xml");

                // Получаем переменную окружения DATABASE_URL
                String rawUrl = System.getenv("DATABASE_URL");

                if (rawUrl != null && rawUrl.startsWith("mysql://")) {
                    // Преобразуем в jdbc-ссылку
                    URI dbUri = new URI(rawUrl);

                    String userInfo = dbUri.getUserInfo(); // логин:пароль
                    String[] userParts = userInfo.split(":");
                    String username = userParts[0];
                    String password = userParts[1];

                    String jdbcUrl = "jdbc:mysql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

                    config.setProperty("hibernate.connection.url", jdbcUrl);
                    config.setProperty("hibernate.connection.username", username);
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

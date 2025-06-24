FROM tomcat:9.0.85-jdk11-temurin

# Удалим стандартные приложения
RUN rm -rf /usr/local/tomcat/webapps/*

# Копируем наш WAR и делаем его основным приложением
COPY target/TopChart-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Открываем порт
EXPOSE 8080

# Указываем команду запуска
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
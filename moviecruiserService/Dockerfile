FROM java:8-jre
WORKDIR usr/src
ENV MYSQL_DATABASE=db_fse_movie
ENV MYSQL_USER=app_root
ENV MYSQL_PASSWORD=root123
ENV MYSQL_CI_URL=jdbc:mysql://localhost:3306/db_fse_movie
ADD ./target/moviecruiserserverapplication-0.0.1-SNAPSHOT.jar /usr/src/moviecruiserserverapplication-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "moviecruiserserverapplication-0.0.1-SNAPSHOT.jar"]

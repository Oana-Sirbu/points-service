FROM openjdk:12
ADD target/points.jar points.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "points.jar"]
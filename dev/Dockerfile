FROM adoptopenjdk:11-jdk-hotspot
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 5050
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","/app.jar"]
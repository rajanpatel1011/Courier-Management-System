FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/courier-management-system-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

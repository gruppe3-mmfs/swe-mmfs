FROM maven:3.9.11-eclipse-temurin-21 AS build-stage
WORKDIR /app
COPY pom.xml ./
COPY api/ ./api/
COPY app/ ./app/
COPY core/ ./core/
COPY storage/ ./storage/
COPY report/ ./report/
COPY test/ ./test/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build-stage /app/app/target/app-1.0-SNAPSHOT.jar ./app.jar
COPY --from=build-stage /app/app/target/lib ./lib
EXPOSE 8080
CMD ["java", "-cp", "app.jar:lib/*", "org.gruppe3.app.Main"]

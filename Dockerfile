# Stage 1: Build backend from root with Maven
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

# Stage 2: Copy and run scripts to create db schema
FROM mysql:9.5 AS sql-schema
COPY ./scripts/ /docker-entrypoint-initdb.d/

# Stage 3: Final runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build-stage /app/app/target/app-1.0-SNAPSHOT.jar ./app.jar
COPY --from=build-stage /app/app/target/lib ./lib
EXPOSE 8080
CMD ["java", "-cp", "app.jar:lib/*", "org.gruppe3.app.Main"]

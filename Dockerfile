# Stage 1: Build backend from root with Maven
FROM maven:3.9.11-eclipse-temurin-21 AS build-stage
WORKDIR /app
COPY pom.xml ./
COPY core/ ./core/
COPY storage/ ./storage/
COPY test/ ./test/
RUN mvn clean package -DskipTests

# Stage 2: Final runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build-stage /app/core/target/core-1.0-SNAPSHOT.jar ./app.jar
COPY --from=build-stage /app/core/target/lib/ ./lib/
COPY --from=build-stage /app/storage/target/lib/ ./lib/
COPY --from=build-stage /app/test/target/lib/ ./lib/
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

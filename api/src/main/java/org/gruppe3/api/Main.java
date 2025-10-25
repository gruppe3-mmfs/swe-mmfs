package org.gruppe3.api;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.gruppe3.api.adapter.EnturClient;
import org.gruppe3.core.port.StopPlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  private static final StopPlaceRepository repository = new EnturClient();
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    final String JAVA_ENV = System.getenv("JAVA_ENV");
    final String ENTUR_ENV = JAVA_ENV;
    final String MYSQL_HOST = System.getenv("MYSQL_HOST");
    final String MYSQL_USER = System.getenv("MYSQL_USER");
    final String MYSQL_PASSWORD = System.getenv("MYSQL_PASSWORD");
    final String MYSQL_DB = System.getenv("MYSQL_DB");

    Javalin app =
        Javalin.create(
                config -> {
                  config.bundledPlugins.enableCors(
                      cors -> {
                        cors.addRule(
                            it -> {
                              if ("dev".equals(JAVA_ENV)) {
                                it.allowHost("http://localhost:5173");
                              } else if ("prod".equals(JAVA_ENV)) {
                                it.allowHost("http://localhost");
                              } else {
                                it.anyHost();
                              }
                            });
                      });
                })
            .start("0.0.0.0", 8080);

    app.get("/api/stop-places/search", Main::searchStopPlaces);
    app.get("/api/stop-places/{id}", Main::getStopPlaceDetails);

    logger.info("Environment (JAVA_ENV): " + JAVA_ENV);
    logger.info("Environment (MYSQL_HOST): " + MYSQL_HOST);
    logger.info("Environment (MYSQL_USER): " + MYSQL_USER);
    logger.info("Environment (MYSQL_PASSWORD): " + MYSQL_PASSWORD);
    logger.info("Environment (MYSQL_DB): " + MYSQL_DB);
    logger.info("Environment (ENTUR_ENV): " + ENTUR_ENV);
  }

  private static void searchStopPlaces(Context ctx) {
    String query = ctx.queryParam("query");
    if (query == null || query.isBlank()) {
      ctx.status(400).result("Query parameter is required");
      return;
    }
    ctx.async(
        () ->
            repository
                .searchStopPlaces(query)
                .thenAccept(stopPlaces -> ctx.json(stopPlaces))
                .exceptionally(
                    throwable -> {
                      logger.error(
                          "Error in searchStopPlaces: {}", throwable.getMessage(), throwable);
                      ctx.status(500).json(new ErrorResponse("Error: " + throwable.getMessage()));
                      return null;
                    }));
  }

  private static void getStopPlaceDetails(Context ctx) {
    String id = ctx.pathParam("id");
    logger.debug("Fetching stop place details for ID: {}", id);
    ctx.async(
        () ->
            repository
                .getStopPlaceDetails(id)
                .thenAccept(
                    stopPlace -> {
                      if (stopPlace == null) {
                        logger.warn("No stop place found for ID: {}", id);
                        ctx.status(404).json(new ErrorResponse("Stop place not found"));
                      } else {
                        ctx.json(stopPlace);
                      }
                    })
                .exceptionally(
                    throwable -> {
                      logger.error(
                          "Error in getStopPlaceDetails for ID {}: {}",
                          id,
                          throwable.getMessage(),
                          throwable);
                      ctx.status(500).json(new ErrorResponse("Error: " + throwable.getMessage()));
                      return null;
                    }));
  }

  // Helper class for error responses
  private record ErrorResponse(String message) {}
}

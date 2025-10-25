package org.gruppe3.core;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {

    String JAVA_ENV = System.getenv("JAVA_ENV");
    String MYSQL_HOST = System.getenv("MYSQL_HOST");
    String MYSQL_USER = System.getenv("MYSQL_USER");
    String MYSQL_PASSWORD = System.getenv("MYSQL_PASSWORD");
    String MYSQL_DB = System.getenv("MYSQL_DB");

    Javalin app =
        Javalin.create(
                config -> {
                  config.bundledPlugins.enableCors(
                      cors -> {
                        cors.addRule(
                            it -> {
                              if (JAVA_ENV == "development") {
                                // npm run dev
                                it.allowHost("http://localhost:5173");
                              } else if (JAVA_ENV == "production") {
                                // npm run preview
                                it.allowHost("http://localhost");
                              } else {
                                // mvn clean package exec:java -pl core (lokal)
                                it.anyHost();
                              }
                            });
                      });
                })
            .start("0.0.0.0", 8080);

    app.get("/api/message", ctx -> ctx.json(new APIResponse("ITF20319-1 25H - gruppe 3")));

    // Log: Info om endepunkt
    logger.info("API Endpoint created -> /api/message");

    logger.info("Environment (JAVA_ENV): " + JAVA_ENV);
    logger.info("Environment (MYSQL_HOST): " + MYSQL_HOST);
    logger.info("Environment (MYSQL_USER): " + MYSQL_USER);
    logger.info("Environment (MYSQL_PASSWORD): " + MYSQL_PASSWORD);
    logger.info("Environment (MYSQL_DB): " + MYSQL_DB);
  }

  // Typ controller(?): må se nærmere på hvordan dette skal gjøres presist
  public record APIResponse(String message) {}
}

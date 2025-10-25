package org.gruppe3.app;

import io.javalin.Javalin;
import org.gruppe3.api.adapter.ping.PingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  private static final String JAVA_ENV = System.getenv("JAVA_ENV");
  private static final String ENTUR_ENV = JAVA_ENV;
  private static final String MYSQL_HOST = System.getenv("MYSQL_HOST");
  private static final String MYSQL_USER = System.getenv("MYSQL_USER");
  private static final String MYSQL_PASSWORD = System.getenv("MYSQL_PASSWORD");
  private static final String MYSQL_DB = System.getenv("MYSQL_DB");

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {

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

    logger.info("Environment (JAVA_ENV): " + JAVA_ENV);
    logger.info("Environment (MYSQL_HOST): " + MYSQL_HOST);
    logger.info("Environment (MYSQL_USER): " + MYSQL_USER);
    logger.info("Environment (MYSQL_PASSWORD): " + MYSQL_PASSWORD);
    logger.info("Environment (MYSQL_DB): " + MYSQL_DB);
    logger.info("Environment (ENTUR_ENV): " + ENTUR_ENV);

    app.get("/api/ping", PingController.pingHandler);
  }
}

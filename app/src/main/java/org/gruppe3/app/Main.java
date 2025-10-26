package org.gruppe3.app;

import io.javalin.Javalin;
import java.sql.Connection;
import org.gruppe3.api.adapter.ping.PingController;
import org.gruppe3.core.dto.CreateUserRequest;
import org.gruppe3.core.exception.UserRepositoryException;
import org.gruppe3.core.port.UserRepositoryPort;
import org.gruppe3.core.service.UserService;
import org.gruppe3.storage.adapter.UserRepositoryMySQLAdapter;
import org.gruppe3.storage.database.MySQLDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final String JAVA_ENV = System.getenv("JAVA_ENV");
  private static final String MYSQL_HOST = System.getenv("MYSQL_HOST");
  private static final String MYSQL_USER = System.getenv("MYSQL_USER");
  private static final String MYSQL_PASSWORD = System.getenv("MYSQL_PASSWORD");
  private static final String MYSQL_DB = System.getenv("MYSQL_DB");
  private static final String MYSQL_URL = "jdbc:mysql://" + MYSQL_HOST + ":3306/" + MYSQL_DB;
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {

    MySQLDatabase database = new MySQLDatabase(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
    Connection dbConnection = database.startDB();

    UserRepositoryPort userRepository = new UserRepositoryMySQLAdapter(dbConnection);
    UserService userService = new UserService(userRepository);

    try {
      userService.createUser(new CreateUserRequest("Donald", "Duck", "128937", "donald@andeby.no"));
    } catch (UserRepositoryException e) {
      logger.error(e.getMessage());
    }

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

    app.get("/api/ping", PingController.pingHandler);
  }
}

package org.gruppe3.app;

import io.javalin.Javalin;
import java.sql.Connection;
import java.util.ArrayList;
import org.gruppe3.api.adapter.ping.PingController;
import org.gruppe3.core.domain.User;
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

  public static void main(String[] args) throws UserRepositoryException {

    MySQLDatabase database = new MySQLDatabase(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);

    Connection dbConnection = database.startDB();

    UserRepositoryPort userRepository = new UserRepositoryMySQLAdapter(dbConnection);

    UserService userService = new UserService(userRepository);

    try {
      userService.createUser(new CreateUserRequest("Donald", "Duck", "128937", "donald@andeby.no"));
      userService.createUser(new CreateUserRequest("Anton", "Duck", "228937", "anton@andeby.no"));
      userService.createUser(new CreateUserRequest("Skrue", "Duck", "328937", "skrue@andeby.no"));
      userService.createUser(new CreateUserRequest("Ole", "Duck", "428937", "ole@andeby.no"));
      userService.createUser(new CreateUserRequest("Dole", "Duck", "528937", "dole@andeby.no"));
      userService.createUser(new CreateUserRequest("Doffen", "Duck", "628937", "doffen@andeby.no"));
    } catch (UserRepositoryException e) {
      logger.error(e.getMessage());
    }

    ArrayList<User> usersWithIdFromDatabase = userRepository.getAllUsersFromDatabase();
    for (User user : usersWithIdFromDatabase) {
      logger.info(
          "ID: " + user.getUserId() + ", Name: " + user.getFirstName() + " " + user.getLastName());
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

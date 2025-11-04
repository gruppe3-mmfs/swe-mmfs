package org.gruppe3.app;

import io.javalin.Javalin;
import java.sql.Connection;
import java.util.ArrayList;
import org.gruppe3.api.PingController;
import org.gruppe3.api.adapter.entur.*;
import org.gruppe3.core.domain.*;
import org.gruppe3.core.dto.*;
import org.gruppe3.core.exception.*;
import org.gruppe3.core.port.out.*;
import org.gruppe3.core.service.*;
import org.gruppe3.storage.adapter.*;
import org.gruppe3.storage.database.MySQLDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

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
    TicketRepositoryPort ticketRepository = new TicketRepositoryMySQLAdapter(dbConnection);
    LocationRepositoryPort locationPort = new EnturLocationAdapter();

    UserService userService = new UserService(userRepository);
    TicketService ticketService = new TicketService(ticketRepository);
    LocationService locationService = new LocationService(locationPort);

    try {
      SearchLocationResult froms =
          locationService.searchLocations(new SearchLocationRequest("Halden stasjon"));
      SearchLocationResult tos =
          locationService.searchLocations(new SearchLocationRequest("Oslo S"));

      logger.info(froms.getLocations().getFirst().getId());
      logger.info(tos.getLocations().getFirst().getId());

    } catch (TripRepositoryException e) {
      logger.error(e.getMessage());
    }

    try {
      userService.createUser(new CreateUserRequest("Donald", "Duck", "128937", "donald@andeby.no"));
      userService.createUser(new CreateUserRequest("Anton", "Duck", "228937", "anton@andeby.no"));
      userService.createUser(new CreateUserRequest("Skrue", "Duck", "328937", "skrue@andeby.no"));
      userService.createUser(new CreateUserRequest("Ole", "Duck", "428937", "ole@andeby.no"));
      userService.createUser(new CreateUserRequest("Dole", "Duck", "528937", "dole@andeby.no"));
      userService.createUser(new CreateUserRequest("Doffen", "Duck", "628937", "doffen@andeby.no"));

      ArrayList<User> usersWithIdFromDatabase = userRepository.getAllUsersFromDatabase();
      for (User user : usersWithIdFromDatabase) {
        logger.info(
            "ID: "
                + user.getUserId()
                + ", Name: "
                + user.getFirstName()
                + " "
                + user.getLastName());
      }
    } catch (UserRepositoryException e) {
      logger.error(e.getMessage());
    }

    try {
      ticketService.createTicket(
          new CreateTicketRequest(
              "2813094bb9d066d29c9b8df77de14975b1fa1746f1ca088acdf6ff253ade0063",
              "Student",
              new Trip(new Location("Bergen"), new Location("Oslo"))));
    } catch (TicketRepositoryException e) {
      logger.error(e.getMessage());
    }

    Javalin app =
        Javalin.create(
                config -> {
                  config.bundledPlugins.enableCors(
                      cors -> {
                        cors.addRule(
                            it -> {
                              it.allowHost("http://localhost");
                            });
                      });
                })
            .start("0.0.0.0", 8080);

    app.get("/api/ping", PingController.pingHandler);
  }
}

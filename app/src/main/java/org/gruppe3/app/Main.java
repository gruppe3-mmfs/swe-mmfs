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

    ArrayList<SearchLocationResult> locationsEntur = new ArrayList<>();

    try {
      logger.info("Søker etter stoppesteder (Entur API: GeoCoder) for lokasjoner");
      SearchLocationResult haldenstasjon =
          locationService.searchLocations(new SearchLocationRequest("Halden stasjon"));
      SearchLocationResult oslos =
          locationService.searchLocations(new SearchLocationRequest("Oslo S"));

      SearchLocationResult bergen =
          locationService.searchLocations(new SearchLocationRequest("Bergen"));
      SearchLocationResult kristiansand =
          locationService.searchLocations(new SearchLocationRequest("Kristiansand"));
      SearchLocationResult stavanger =
          locationService.searchLocations(new SearchLocationRequest("Stavanger"));
      SearchLocationResult kirkenes =
          locationService.searchLocations(new SearchLocationRequest("Kirkenes"));

      logger.info(
          "Fant følgende for 'Halden stasjon': "
              + haldenstasjon.getLocations().getFirst().getDescription());
      logger.info(
          "Fant følgende for 'Oslo S': " + oslos.getLocations().getFirst().getDescription());

      logger.info("Fant følgende for 'Bergen': " + bergen.getLocations().getFirst());
      logger.info("Fant følgende for 'Kristiansand': " + kristiansand.getLocations().getFirst());
      logger.info("Fant følgende for 'Stavanger': " + stavanger.getLocations().getFirst());
      logger.info("Fant følgende for 'Kirkenes': " + kirkenes.getLocations().getFirst());

      locationsEntur.add(haldenstasjon);
      locationsEntur.add(oslos);
      locationsEntur.add(bergen);
      locationsEntur.add(kristiansand);
      locationsEntur.add(stavanger);
      locationsEntur.add(kirkenes);

    } catch (TripRepositoryException e) {
      logger.error(e.getMessage());
    }

    try {
      logger.info("Oppretter bruker (Donald Duck)");
      userService.createUser(new CreateUserRequest("Donald", "Duck", "128937", "donald@andeby.no"));
      logger.info("Oppretter bruker (Anton Duck)");
      userService.createUser(new CreateUserRequest("Anton", "Duck", "228937", "anton@andeby.no"));
      logger.info("Oppretter bruker (Skrue Duck)");
      userService.createUser(new CreateUserRequest("Skrue", "Duck", "328937", "skrue@andeby.no"));
      logger.info("Oppretter bruker (Ole Duck)");
      userService.createUser(new CreateUserRequest("Ole", "Duck", "428937", "ole@andeby.no"));
      logger.info("Oppretter bruker (Dole Duck)");
      userService.createUser(new CreateUserRequest("Dole", "Duck", "528937", "dole@andeby.no"));
      logger.info("Oppretter bruker (Doffen Duck)");
      userService.createUser(new CreateUserRequest("Doffen", "Duck", "628937", "doffen@andeby.no"));

      ArrayList<User> usersWithIdFromDatabase = userRepository.getAllUsersFromDatabase();
      for (User user : usersWithIdFromDatabase) {
        logger.info(
            "ID fra database: "
                + user.getUserId()
                + ", Name: "
                + user.getFirstName()
                + " "
                + user.getLastName());
      }
    } catch (UserRepositoryException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    }

    try {
      logger.info("Angir familie id for bruker id 1: " + userRepository.getUserById(1));
      userService.assignUserToFamily(new AssignUserToFamilyRequest(1, 1));
      logger.info("Angir familie id for bruker id 2: " + userRepository.getUserById(2));
      userService.assignUserToFamily(new AssignUserToFamilyRequest(2, 1));
    } catch (UserRepositoryException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    }

    try {
      logger.info(
          "Kjøper billett (brukerid 1: ("
              + userRepository.getUserById(1)
              + "), fra Halden stasjon til Oslo S");
      ticketService.buyTicket(
          new BuyTicketRequest(
              1,
              "Student",
              locationsEntur.get(0).getLocations().getFirst().getDescription(), // Halden stasjon
              locationsEntur.get(1).getLocations().getFirst().getDescription())); // Oslo S
      ticketService.buyTicket(
          new BuyTicketRequest(
              1,
              "Student",
              locationsEntur.get(2).getLocations().getFirst().getDescription(), // Bergen
              locationsEntur.get(3).getLocations().getFirst().getDescription())); // Kristiansand
      ticketService.buyTicket(
          new BuyTicketRequest(
              1,
              "Student",
              locationsEntur.get(4).getLocations().getFirst().getDescription(), // Stavanger
              locationsEntur.get(5).getLocations().getFirst().getDescription())); // Kirkenes
    } catch (TicketRepositoryException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    } catch (BuyTicketRequestException e) {
      logger.error(e.getMessage());
    } catch (UserRepositoryException e) {
      logger.error(e.getMessage());
    }

    try {
      logger.info("Deler billett (ticketid 2) fra brukerid 1 til brukerid 2");
      ticketService.shareTicket(new ShareTicketWithUserRequest(1, 2, 2));
      logger.info("Deler billett (ticketid 3) fra brukerid 1 til brukerid 3");
      ticketService.shareTicket(new ShareTicketWithUserRequest(1, 3, 3));
      logger.info("Forsøker å dele billett (ticketid 2) fra brukerid 1 til brukerid 2 på nytt");
      ticketService.shareTicket(new ShareTicketWithUserRequest(1, 2, 2));
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

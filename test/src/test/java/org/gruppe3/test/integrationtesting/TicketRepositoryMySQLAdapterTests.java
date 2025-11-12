package org.gruppe3.test.integrationtesting;

import java.sql.Connection;
import org.gruppe3.core.domain.*;
import org.gruppe3.storage.adapter.TicketRepositoryMySQLAdapter;
import org.gruppe3.storage.adapter.UserRepositoryMySQLAdapter;
import org.gruppe3.test.testdb.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TicketRepositoryMySQLAdapterTests {

  private static final TestDatabase testDB = new H2TestDatabase();
  private static Connection connection;
  private static TicketRepositoryMySQLAdapter ticketRepository;
  private static UserRepositoryMySQLAdapter userRepository;

  @BeforeAll
  public static void setUpTestDB() throws Exception {
    // Starter test-databasen
    connection = testDB.startDB();
    testDB.createLookupTable();
    testDB.createFamilyTable();
    testDB.insertIntoLookupTable();
    testDB.createUserTable();
    testDB.createTicketTable();

    // Tillater å kunne rulle tilbake endringer senere
    connection.setAutoCommit(false);

    ticketRepository = new TicketRepositoryMySQLAdapter(connection);
    userRepository = new UserRepositoryMySQLAdapter(connection);
  }

  @BeforeEach
  public void prepareTest() throws Exception {
    User user1 = new User("Donald", "Duck", "128937", "donald@andeby.no");
    User user2 = new User("Anton", "Duck", "228937", "anton@andeby.no");
    userRepository.createUser(user1);
    userRepository.createUser(user2);
  }

  @AfterEach
  public void cleanUpTest() throws Exception {
    connection.rollback();
    connection.createStatement().execute("ALTER TABLE USERS ALTER COLUMN USERID RESTART WITH 1");
  }

  @AfterAll
  public static void tearDownTestDB() throws Exception {
    testDB.stopDB();
  }

  @Test
  @DisplayName("createTicketSuccessfully")
  public void createTicketInDatabaseSuccessfully() throws Exception {

    // Arrange
    Location location1 = new Location("Halden Stasjon");
    Location location2 = new Location("Oslo S");
    Trip trip1 = new Trip(location1, location2);
    Ticket ticket1 = new Ticket(1, "dummyhash1", "Normal", trip1);

    // Act
    ticketRepository.buyTicket(1, ticket1);

    // Assert
    Assertions.assertEquals(1, ticketRepository.getUserTickets(1).getFirst().getTicketOwnerId());
  }

  @Test
  @DisplayName("shareTicketSuccessfully")
  public void shareTicketInDatabaseSuccessfully() throws Exception {

    // Arrange
    Location location1 = new Location("Halden Stasjon");
    Location location2 = new Location("Oslo S");
    Trip trip1 = new Trip(location1, location2);
    Ticket ticket1 = new Ticket(1, "dummyhash1", "Normal", trip1, 1);

    // Forsøker å kjøpe en ticket for bruker med userId 1
    ticketRepository.buyTicket(1, ticket1);

    // Act
    // Forsøker så å dele ticketen med bruker med userId 2
    ticketRepository.shareTicket(1, ticket1, 2);

    // Assert
    // Sjekker at ticketen nå også eies av bruker med userId 2
    Ticket sharedTicket = ticketRepository.getUserTickets(2).getFirst();
    Assertions.assertEquals(2, sharedTicket.getTicketOwnerId());
  }
}
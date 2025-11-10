package org.gruppe3.test.unittesting;

import java.util.ArrayList;
import org.gruppe3.core.domain.Location;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.domain.Trip;
import org.gruppe3.core.dto.BuyTicketRequest;
import org.gruppe3.core.dto.CreateTicketRequest;
import org.gruppe3.core.dto.GetUserTicketsRequest;
import org.gruppe3.core.dto.GetUserTicketsResult;
import org.gruppe3.core.dto.ShareTicketWithUserRequest;
import org.gruppe3.core.exception.BuyTicketRequestException;
import org.gruppe3.core.exception.TicketRepositoryException;
import org.gruppe3.core.port.out.TicketRepositoryPort;
import org.gruppe3.core.service.TicketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceUnitTests {

  @Mock TicketRepositoryPort ticketRepositoryMock;


  @Test
  @DisplayName("createTicket - should create ticket successfully")
  public void createTicketSuccessfully() throws TicketRepositoryException {

    // Arrange
    TicketService ticketService = new TicketService(ticketRepositoryMock);

    // Her oppretter vi stub data for å lage en ticket
    Location fromLocation = new Location("Oslo S");
    Location toLocation = new Location("Halden Stasjon");
    Trip trip = new Trip(fromLocation, toLocation);

    CreateTicketRequest request =
        new CreateTicketRequest("GeneratedHashCode", "Normal", trip);

    // Act
    ticketService.createTicket(request);

    // Assert
    // Fanger opp argumentet som ble sendt til createTicket i repositoryet
    ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
    Mockito.verify(ticketRepositoryMock, Mockito.times(1)).createTicket(ticketCaptor.capture());

    Ticket capturedTicket = ticketCaptor.getValue();

    // Sjekker at verdiene i det fangede Ticket-objektet er som forventet
    Assertions.assertEquals("GeneratedHashCode", capturedTicket.getTicketHash());
    Assertions.assertEquals("Normal", capturedTicket.getTicketType());
    Assertions.assertEquals(trip, capturedTicket.getTicketTrip());
  }


  @Test
  @DisplayName("getUserTickets - should return correct tickets for user")
  public void getUserTicketsSuccessfully() throws TicketRepositoryException {

    // Arrange
    // Her oppretter vi stub data som skal returneres av mock objektet
    ArrayList<Ticket> stubbedTickets = new ArrayList<>();

    Location loc1 = new Location("Station A");
    Location loc2 = new Location("Station B");
    Location loc3 = new Location("Station C");
    Location loc4 = new Location("Station D");
    Location loc5 = new Location("Station E");
    Location loc6 = new Location("Station F");

    Trip trip1 = new Trip(loc1, loc2);
    Trip trip2 = new Trip(loc3, loc4);
    Trip trip3 = new Trip(loc5, loc6);

    stubbedTickets.add(new Ticket(1, "123abc", "Normal", trip1, 1));
    stubbedTickets.add(new Ticket(2, "456def", "Student", trip2, 1));
    stubbedTickets.add(new Ticket(3, "789ghi", "Senior", trip3, 1));

    Mockito.when(ticketRepositoryMock.getUserTickets(1)).thenReturn(stubbedTickets);

    TicketService ticketService = new TicketService(ticketRepositoryMock);

    int userId = 1;
    GetUserTicketsRequest request = new GetUserTicketsRequest(userId);

    // Act
    GetUserTicketsResult result = ticketService.getUserTickets(request);

    // Assert
    // Sjekker her om resultatet er som forventet
    Assertions.assertEquals(1, result.getUserId());
    Assertions.assertEquals(3, result.getTickets().size());
    Assertions.assertEquals(
        "Normal",
        result.getTickets().get(0).getTicketType(),
        "First ticket type should be 'Normal'");
    Assertions.assertEquals(
        "Student",
        result.getTickets().get(1).getTicketType(),
        "Second ticket type should be 'Student'");
    Assertions.assertEquals(
        "Senior",
        result.getTickets().get(2).getTicketType(),
        "Third ticket type should be 'Senior'");
  }

  @Test
  @DisplayName("buyTicket - should buy ticket successfully")
  public void buyTicketSuccessfully() throws TicketRepositoryException, BuyTicketRequestException {

    // Arrange
    TicketRepositoryPort ticketRepositoryMock = Mockito.mock(TicketRepositoryPort.class);
    TicketService ticketService = new TicketService(ticketRepositoryMock);
    BuyTicketRequest request = new BuyTicketRequest(1, "Normal", "Oslo S", "Halden Stasjon");

    // Act
    ticketService.buyTicket(request);

    // Assert
    // Fanger opp argumentene som ble sendt til buyTicket i repositoryet
    ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
    ArgumentCaptor<Integer> userIdCaptor = ArgumentCaptor.forClass(Integer.class);

    // Verifiserer at buyTicket i repositoryet ble kalt med riktig userId og Ticket
    Mockito.verify(ticketRepositoryMock, Mockito.times(1))
        .buyTicket(userIdCaptor.capture(), ticketCaptor.capture());

    // Henter verdiene som ble sendt og fanget opp
    Integer capturedUserId = userIdCaptor.getValue();
    Ticket capturedTicket = ticketCaptor.getValue();

    // Sjekker at userId er som forventet
    Assertions.assertEquals(1, capturedUserId);
    // Sjekker at ticketType i Ticket-objektet er som forventet
    Assertions.assertEquals("Normal", capturedTicket.getTicketType());

    // Sjekker så tripp feltene i Ticket-objektet
    Trip capturedTrip = capturedTicket.getTicketTrip();
    Assertions.assertEquals("Oslo S", capturedTrip.getFromLocation().getName());
    Assertions.assertEquals("Halden Stasjon", capturedTrip.getToLocation().getName());
  }

  @Test
  @DisplayName("hasStringSHA256 - should hash string correctly")
  void hashStringSHA256Successfully() throws BuyTicketRequestException {
    // Arrange
    // Skriver inn et testinput og forventet hashverdi
    String input = "TestString123";
    String expectedHash =
        "36a36a9ff0d1080272653c8cc883bd8f3dcc75d95e7af626a6ee2da87052fa75";

    // Act
    // Kaller hashStringSHA256 for å få den faktiske hashen
    String actualHash = BuyTicketRequest.hashStringSHA256(input);

    // Assert
    // Sjekker at den faktiske hashen stemmer overens med forventet verdi
    Assertions.assertEquals(expectedHash, actualHash, "The SHA-256 hash should match the expected value");
  }

   @Test
    @DisplayName("testTicketRepositoryException - should create exception with message and cause")
    void ticketRepositoryExceptionSuccessfully() {

        // Arrange
        // Kaller konstruktøren med både message og cause
        Exception cause = new Exception("Test2 feil");
        TicketRepositoryException test2 = new TicketRepositoryException("Test2 message", cause);

        // Act & assert
        // Sjekker at message og cause er satt riktig
        assert test2.getMessage().equals("Test2 message") : "Message should be 'Test2 message'";
        assert test2.getCause() == cause : "Cause should be the same as the one provided";
    }

    @Test
    @DisplayName("testShareTicketWithUser - should share ticket successfully")
    void shareTicketWithUserSuccessfully() throws TicketRepositoryException {

        // Arrange
        TicketRepositoryPort ticketRepositoryMock = Mockito.mock(TicketRepositoryPort.class);
        TicketService ticketService = new TicketService(ticketRepositoryMock);

        // Lager stub data for brukerens billett
        int userId = 1;
        int ticketIdToShare = 2;
        String ticketHashToShare = "hash1";
        String ticketTypeToShare = "Normal";
        Trip ticketTripToShare = new Trip(new Location("A"), new Location("B"));
        int newOwnerUserId = 3;

        // Bruker eierens billett
        ArrayList<Ticket> userTickets = new ArrayList<>();
        userTickets.add(new Ticket(ticketIdToShare, ticketHashToShare, ticketTypeToShare, ticketTripToShare, userId));
        when(ticketRepositoryMock.getUserTickets(userId)).thenReturn(userTickets);
        ShareTicketWithUserRequest request = new ShareTicketWithUserRequest(userId, ticketIdToShare, newOwnerUserId);

        // Act
        // Kaller shareTicket-funksjonen i TicketService med riktig request
        ticketService.shareTicket(request);

        // Assert
        // Verifiserer at shareTicket i repositoryet ble kalt med riktig userId, Ticket og newOwnerUserId
        verify(ticketRepositoryMock, times(1)).shareTicket(eq(userId), any(Ticket.class), eq(newOwnerUserId));
    }
}

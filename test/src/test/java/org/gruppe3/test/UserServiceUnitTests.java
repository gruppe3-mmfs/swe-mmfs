package org.gruppe3.test;

import java.util.ArrayList;
import org.gruppe3.core.domain.Location;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.domain.Trip;
import org.gruppe3.core.dto.GetUserTicketsRequest;
import org.gruppe3.core.dto.GetUserTicketsResult;
import org.gruppe3.core.port.out.TicketRepositoryPort;
import org.gruppe3.core.service.TicketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

  @Mock TicketRepositoryPort ticketRepositoryMock;

  @Test
  @DisplayName("getUserTickets - should return correct tickets for user")
  public void getUserTickets() throws Exception {

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
    Assertions.assertEquals(3, result.getTicketDTOs().size());
    Assertions.assertEquals(
        "Normal",
        result.getTicketDTOs().get(0).getTicketType(),
        "First ticket type should be 'Normal'");
    Assertions.assertEquals(
        "Student",
        result.getTicketDTOs().get(1).getTicketType(),
        "Second ticket type should be 'Student'");
    Assertions.assertEquals(
        "Senior",
        result.getTicketDTOs().get(2).getTicketType(),
        "Third ticket type should be 'Senior'");
  }
}

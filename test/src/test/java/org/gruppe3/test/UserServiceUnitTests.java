package org.gruppe3.test;

import java.util.ArrayList;
import org.gruppe3.core.domain.Route;
import org.gruppe3.core.domain.Ticket;
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
    Route route1 = new Route("Station A", "Station B");
    Route route2 = new Route("Station C", "Station D");
    Route route3 = new Route("Station E", "Station F");
    stubbedTickets.add(new Ticket(1, "123abc", "Normal", route1, 1));
    stubbedTickets.add(new Ticket(2, "456def", "Student", route2, 1));
    stubbedTickets.add(new Ticket(3, "789ghi", "Senior", route3, 1));
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

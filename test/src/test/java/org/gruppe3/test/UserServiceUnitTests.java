package org.gruppe3.test;

import java.util.ArrayList;

import org.gruppe3.core.domain.Route;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.dto.GetUserTicketsRequest;
import org.gruppe3.core.dto.GetUserTicketsResult;
import org.gruppe3.core.port.TicketRepositoryPort;
import org.gruppe3.core.port.UserRepositoryPort;
import org.gruppe3.core.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

  @Mock UserRepositoryPort userRepositoryMock;
  @Mock TicketRepositoryPort ticketRepositoryMock;

  @Test
  public void getUserTickets() throws Exception {

    // Arrange
    // Her oppretter vi stub data som skal returneres av mock objektet
    ArrayList<Ticket> stubbedTickets = new ArrayList<>();
    Route route1 = new Route(1, "Station A", "Station B");
    Route route2 = new Route(2, "Station C", "Station D");
    Route route3 = new Route(3, "Station E", "Station F");
    stubbedTickets.add(new Ticket(1,1, "123abc", "Normal", route1));
    stubbedTickets.add(new Ticket(1, 2, "456def", "Student", route2));
    stubbedTickets.add(new Ticket(1, 3, "789ghi", "Senior", route3));
    Mockito.when(ticketRepositoryMock.getUserTickets(1)).thenReturn(stubbedTickets);

    UserService userService = new UserService(userRepositoryMock, ticketRepositoryMock);

    int userId = 1;
    GetUserTicketsRequest request = new GetUserTicketsRequest(userId);

    // Act
    GetUserTicketsResult result = userService.getUserTickets(request);

    // Assert
    // Sjekker her om resultatet er som forventet
    Assertions.assertEquals( 1,result.getUserId());
    Assertions.assertEquals(3, result.getTicketDTOs().size());
    Assertions.assertEquals("Normal", result.getTicketDTOs().get(0).getTicketType());
    Assertions.assertEquals("Student", result.getTicketDTOs().get(1).getTicketType());
    Assertions.assertEquals("Senior", result.getTicketDTOs().get(2).getTicketType());
    
  }
}

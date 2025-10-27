package org.gruppe3.core.service;

import java.util.ArrayList;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.domain.User;
import org.gruppe3.core.dto.CreateUserRequest;
import org.gruppe3.core.dto.GetUserTicketsRequest;
import org.gruppe3.core.dto.GetUserTicketsResult;
import org.gruppe3.core.dto.TicketDTO;
import org.gruppe3.core.exception.TicketRepositoryException;
import org.gruppe3.core.exception.UserRepositoryException;
import org.gruppe3.core.port.TicketRepositoryPort;
import org.gruppe3.core.port.UserRepositoryPort;

public class UserService {

  private UserRepositoryPort userRepository;
  private TicketRepositoryPort ticketRepository;

  public UserService(UserRepositoryPort userRepository) {
    this.userRepository = userRepository;
  }

  public UserService(UserRepositoryPort userRepository, TicketRepositoryPort ticketRepository) {
    this.userRepository = userRepository;
    this.ticketRepository = ticketRepository;
  }

  public void createUser(CreateUserRequest request) throws UserRepositoryException {
    User user =
        new User(
            request.getFirstName(),
            request.getLastName(),
            request.getPhoneNumber(),
            request.getEmail());
    userRepository.createUser(user);
  }

  public GetUserTicketsResult getUserTickets(GetUserTicketsRequest request)
      throws TicketRepositoryException {

    ArrayList<Ticket> userTickets = ticketRepository.getUserTickets(request.getUserId());

    ArrayList<TicketDTO> userTicketsResult = new ArrayList<>();

    for (Ticket ticket : userTickets) {
      TicketDTO ticketDTO =
          new TicketDTO(ticket.getTicketId(), ticket.getTicketType(), ticket.getTicketRoute());
      userTicketsResult.add(ticketDTO);
    }

    GetUserTicketsResult result = new GetUserTicketsResult(request.getUserId(), userTicketsResult);

    return result;
  }
}


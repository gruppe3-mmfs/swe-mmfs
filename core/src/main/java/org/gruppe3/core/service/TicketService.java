package org.gruppe3.core.service;

import java.util.ArrayList;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.domain.User;
import org.gruppe3.core.dto.BuyTicketRequest;
import org.gruppe3.core.dto.BuyTicketResult;
import org.gruppe3.core.dto.CreateTicketRequest;
import org.gruppe3.core.dto.GetUserTicketsRequest;
import org.gruppe3.core.dto.GetUserTicketsResult;
import org.gruppe3.core.dto.TicketDTO;
import org.gruppe3.core.exception.TicketRepositoryException;
import org.gruppe3.core.port.TicketRepositoryPort;
import org.gruppe3.core.port.UserRepositoryPort;

public class TicketService {

  private TicketRepositoryPort ticketRepository;

  public TicketService(TicketRepositoryPort ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public void createTicket(CreateTicketRequest request) throws TicketRepositoryException {
    Ticket ticket =
        new Ticket(request.getTicketHash(), request.getTicketType(), request.getTicketRoute());
    ticketRepository.createTicket(ticket);
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

  public BuyTicketResult buyTicket(BuyTicketRequest request) throws TicketRepositoryException {

    // Sjekker om bruker med riktig ID eksisterer
    ArrayList<User> user = ticketRepository.getUserById(request.getUserId());
    if (user == null) {
      throw TicketRepositoryException.userNotFound(request.getUserId());
    }

    // Oppretter ticket
    Ticket ticket =
        new Ticket(
            request.getTicketOwnerId(),
            request.getTicketId(),
            request.getTicketHash(),
            request.getTicketType(),
            request.getTicketRoute());

    // Lagrer ticket gjennom port
    Ticket savedTicket = ticketRepository.saveTicket(ticket);

    return new BuyTicketResult(
        savedTicket.getTicketId(),
        savedTicket.getTicketType(),
        savedTicket.getTicketRoute(),
        "Ticket successfully purchased and saved!");
  }
}

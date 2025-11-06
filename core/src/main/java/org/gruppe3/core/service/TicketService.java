package org.gruppe3.core.service;

import java.util.ArrayList;
import org.gruppe3.core.domain.Location;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.domain.Trip;
import org.gruppe3.core.dto.BuyTicketRequest;
import org.gruppe3.core.dto.CreateTicketRequest;
import org.gruppe3.core.dto.GetUserTicketsRequest;
import org.gruppe3.core.dto.GetUserTicketsResult;
import org.gruppe3.core.dto.ShareTicketWithUserRequest;
import org.gruppe3.core.exception.TicketRepositoryException;
import org.gruppe3.core.port.out.TicketRepositoryPort;

public class TicketService {

  private TicketRepositoryPort ticketRepository;

  public TicketService(TicketRepositoryPort ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public void createTicket(CreateTicketRequest request) throws TicketRepositoryException {
    Ticket ticket =
        new Ticket(request.getTicketHash(), request.getTicketType(), request.getTicketTrip());
    ticketRepository.createTicket(ticket);
  }

  public GetUserTicketsResult getUserTickets(GetUserTicketsRequest request)
      throws TicketRepositoryException {

    ArrayList<Ticket> userTickets = ticketRepository.getUserTickets(request.getUserId());

    ArrayList<Ticket> userTicketsResult = new ArrayList<>();

    for (Ticket ticket : userTickets) {
      Ticket newTicket =
          new Ticket(
              ticket.getTicketId(),
              ticket.getTicketHash(),
              ticket.getTicketType(),
              ticket.getTicketTrip());
      userTicketsResult.add(
          newTicket); /* benytter ikke DTO her siden vi ønsker all informasjon (men kunne likefullt gjort det gjennom en DTO) */
    }

    GetUserTicketsResult result = new GetUserTicketsResult(request.getUserId(), userTicketsResult);

    return result;
  }

  public void buyTicket(BuyTicketRequest request) throws TicketRepositoryException {

    Location origin = new Location(request.getTicketTripOrigin());
    Location destination = new Location(request.getTicketTripDestination());
    Trip trip = new Trip(origin, destination);

    Ticket ticket = new Ticket(request.getTicketHash(), request.getTicketType(), trip);

    ticketRepository.buyTicket(request.getUserId(), ticket);
  }

  public void shareTicket(ShareTicketWithUserRequest request) throws TicketRepositoryException {

    ArrayList<Integer> userTicketAvailableToShare = new ArrayList<>();

    // Henter brukerens billetter
    ArrayList<Ticket> userTickets = ticketRepository.getUserTickets(request.getUserId());

    // Sjekker om brukeren eier billetten som skal deles
    for (Ticket ticket : userTickets) {
      userTicketAvailableToShare.add(ticket.getTicketId());
    }

    if (!userTicketAvailableToShare.contains(request.getTicketId())) {
      throw new TicketRepositoryException("Cannot share ticket not owned by user");
    } else {

      ticketRepository.shareTicket(
          request.getUserId(), new Ticket(request.getTicketId()), request.getNewOwnerUserId());
    }
  }
}

package org.gruppe3.core.service;

import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.dto.CreateTicketRequest;
import org.gruppe3.core.exception.TicketRepositoryException;
import org.gruppe3.core.port.TicketRepositoryPort;

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
}

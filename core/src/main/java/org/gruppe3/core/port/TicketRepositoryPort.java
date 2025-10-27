package org.gruppe3.core.port;

import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.exception.TicketRepositoryException;

public interface TicketRepositoryPort {

  void createTicket(Ticket ticket) throws TicketRepositoryException;
}

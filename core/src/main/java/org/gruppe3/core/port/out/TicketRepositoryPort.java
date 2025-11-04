package org.gruppe3.core.port.out;

import java.util.ArrayList;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.exception.TicketRepositoryException;

public interface TicketRepositoryPort {

  void createTicketInDatabase(Ticket ticket) throws TicketRepositoryException;

  ArrayList<Ticket> getUserTickets(int userId) throws TicketRepositoryException;

  // void buyTicket(User user, Ticket ticket) throws TicketRepositoryException;
}

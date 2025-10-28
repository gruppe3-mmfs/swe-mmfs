package org.gruppe3.core.port;

import java.util.ArrayList;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.domain.User;
import org.gruppe3.core.exception.TicketRepositoryException;

public interface TicketRepositoryPort {

  void createTicket(Ticket ticket) throws TicketRepositoryException;

  ArrayList<Ticket> getUserTickets(int userId) throws TicketRepositoryException;

  ArrayList<User> getUserById(int userId) throws TicketRepositoryException;

  Ticket saveTicket(Ticket ticket) throws TicketRepositoryException;
}

package org.gruppe3.core.dto;

import java.util.ArrayList;
import org.gruppe3.core.domain.Ticket;

public class GetUserTicketsResult {

  private final int userId;
  private final ArrayList<Ticket> tickets;

  public GetUserTicketsResult(int userId, ArrayList<Ticket> tickets) {
    this.userId = userId;
    this.tickets = tickets;
  }

  public int getUserId() {
    return userId;
  }

  public ArrayList<Ticket> getTickets() {
    return tickets;
  }
}

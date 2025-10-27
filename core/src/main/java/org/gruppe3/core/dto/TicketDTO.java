package org.gruppe3.core.dto;

import org.gruppe3.core.domain.Route;

public class TicketDTO {

  private final int ticketId;
  private final String ticketType;
  private final Route ticketRoute;

  public TicketDTO(int ticketId, String ticketType, Route ticketRoute) {
    this.ticketId = ticketId;
    this.ticketType = ticketType;
    this.ticketRoute = ticketRoute;
  }

  public int getTicketId() {
    return ticketId;
  }

  public String getTicketType() {
    return ticketType;
  }

  public Route getTicketRoute() {
    return ticketRoute;
  }
}

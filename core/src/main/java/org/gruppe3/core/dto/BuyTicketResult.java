package org.gruppe3.core.dto;

import org.gruppe3.core.domain.Route;

public class BuyTicketResult {

  private final int ticketId;
  private final String ticketType;
  private final Route ticketRoute;
  private final String message;

  public BuyTicketResult(int ticketId, String ticketType, Route ticketRoute, String message) {
    this.ticketId = ticketId;
    this.ticketRoute = ticketRoute;
    this.ticketType = ticketType;
    this.message = message;
  }

  public int getTicketId() {
    return ticketId;
  }

  public Route getTicketRoute() {
    return ticketRoute;
  }

  public String getTicketType() {
    return ticketType;
  }

  public String getMessage() {
    return message;
  }
}

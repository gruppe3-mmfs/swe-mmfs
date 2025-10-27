package org.gruppe3.core.dto;

import org.gruppe3.core.domain.Route;

public class CreateTicketRequest {

  private final String ticketHash;
  private final String ticketType;
  private final Route ticketRoute;

  public CreateTicketRequest(String ticketHash, String ticketType, Route ticketRoute) {
    this.ticketHash = ticketHash;
    this.ticketType = ticketType;
    this.ticketRoute = ticketRoute;
  }

  public String getTicketHash() {
    return ticketHash;
  }

  public String getTicketType() {
    return ticketType;
  }

  public Route getTicketRoute() {
    return ticketRoute;
  }
}

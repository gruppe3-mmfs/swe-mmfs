package org.gruppe3.core.dto;

import org.gruppe3.core.domain.Route;

public class BuyTicketRequest {

  private final int userId;
  private final int ticketOwnerId;
  private final int ticketId;
  private final String ticketHash;
  private final String ticketType;
  public Route ticketRoute;

  public BuyTicketRequest(
      int userId,
      int ticketOwnerId,
      int ticketId,
      String ticketHash,
      String ticketType,
      Route ticketRoute) {
    this.userId = userId;
    this.ticketOwnerId = ticketOwnerId;
    this.ticketId = ticketId;
    this.ticketHash = ticketHash;
    this.ticketType = ticketType;
    this.ticketRoute = ticketRoute;
  }

  public int getUserId() {
    return userId;
  }

  public int getTicketOwnerId() {
    return ticketOwnerId;
  }

  public int getTicketId() {
    return ticketId;
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

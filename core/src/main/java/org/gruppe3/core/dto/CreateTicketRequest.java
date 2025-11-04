package org.gruppe3.core.dto;

import org.gruppe3.core.domain.Trip;

public class CreateTicketRequest {

  private final String ticketHash;
  private final String ticketType;
  private final Trip ticketTrip;

  public CreateTicketRequest(String ticketHash, String ticketType, Trip ticketTrip) {
    this.ticketHash = ticketHash;
    this.ticketType = ticketType;
    this.ticketTrip = ticketTrip;
  }

  public String getTicketHash() {
    return ticketHash;
  }

  public String getTicketType() {
    return ticketType;
  }

  public Trip getTicketTrip() {
    return ticketTrip;
  }
}

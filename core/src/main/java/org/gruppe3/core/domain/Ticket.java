package org.gruppe3.core.domain;

public class Ticket {

  private int ticketOwnerId;
  private int ticketId;
  private String ticketHash;
  private String ticketType;
  private Route ticketRoute;

  public Ticket(
      int ticketOwnerId, int ticketId, String ticketHash, String ticketType, Route ticketRoute) {
    this.ticketOwnerId = ticketOwnerId;
    this.ticketId = ticketId;
    this.ticketHash = ticketHash;
    this.ticketType = ticketType;
    this.ticketRoute = ticketRoute;
  }

  public Ticket(int ticketId, String ticketHash, String ticketType, Route ticketRoute) {
    this.ticketId = ticketId;
    this.ticketHash = ticketHash;
    this.ticketType = ticketType;
    this.ticketRoute = ticketRoute;
  }

  public Ticket(String ticketHash, String ticketType, Route ticketRoute) {
    this.ticketHash = ticketHash;
    this.ticketType = ticketType;
    this.ticketRoute = ticketRoute;
  }

  public int getTicketId() {
    return ticketId;
  }

  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }

  public String getTicketHash() {
    return ticketHash;
  }

  public void setTicketHash(String ticketHash) {
    this.ticketHash = ticketHash;
  }

  public String getTicketType() {
    return ticketType;
  }

  public void setTicketType(String ticketType) {
    this.ticketType = ticketType;
  }

  public Route getTicketRoute() {
    return ticketRoute;
  }

  public void setTicketRoute(Route ticketRoute) {
    this.ticketRoute = ticketRoute;
  }

  public int getTicketOwnerId() {
    return ticketOwnerId;
  }

  public void setTicketOwnerId(int ticketOwnerId) {
    this.ticketOwnerId = ticketOwnerId;
  }
}

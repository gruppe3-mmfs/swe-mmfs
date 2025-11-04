package org.gruppe3.core.domain;

public class Ticket {

  private int ticketId;
  private String ticketHash;
  private String ticketType;
  private Trip ticketTrip;
  private int ticketOwnerId;

  public Ticket(
      int ticketId, String ticketHash, String ticketType, Trip ticketTrip, int ticketOwnerId) {
    this.ticketId = ticketId;
    this.ticketHash = ticketHash;
    this.ticketType = ticketType;
    this.ticketTrip = ticketTrip;
    this.ticketOwnerId = ticketOwnerId;
  }

  public Ticket(int ticketId, String ticketHash, String ticketType, Trip ticketTrip) {
    this.ticketId = ticketId;
    this.ticketHash = ticketHash;
    this.ticketType = ticketType;
    this.ticketTrip = ticketTrip;
  }

  public Ticket(String ticketHash, String ticketType, Trip ticketTrip) {
    this.ticketHash = ticketHash;
    this.ticketType = ticketType;
    this.ticketTrip = ticketTrip;
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

  public Trip getTicketTrip() {
    return ticketTrip;
  }

  public void setTicketTrip(Trip ticketTrip) {
    this.ticketTrip = ticketTrip;
  }

  public int getTicketOwnerId() {
    return ticketOwnerId;
  }

  public void setTicketOwnerId(int ticketOwnerId) {
    this.ticketOwnerId = ticketOwnerId;
  }
}

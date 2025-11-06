package org.gruppe3.core.dto;

public class ShareTicketWithUserRequest {

  private final int userId;
  private final int ticketId;
  private final int newOwnerUserId;

  public ShareTicketWithUserRequest(int userId, int ticketId, int newOwnerUserId) {
    this.userId = userId;
    this.ticketId = ticketId;
    this.newOwnerUserId = newOwnerUserId;
  }

  public int getUserId() {
    return userId;
  }

  public int getTicketId() {
    return ticketId;
  }

  public int getNewOwnerUserId() {
    return newOwnerUserId;
  }
}

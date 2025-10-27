package org.gruppe3.core.dto;

public class GetUserTicketsRequest {
  private final int userId;

  public GetUserTicketsRequest(int userId) {
    this.userId = userId;
  }

  public int getUserId() {
    return userId;
  }
}

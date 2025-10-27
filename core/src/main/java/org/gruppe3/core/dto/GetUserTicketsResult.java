package org.gruppe3.core.dto;

import java.util.ArrayList;

public class GetUserTicketsResult {

  private final int userId;
  private final ArrayList<TicketDTO> ticketDTOs;

  public GetUserTicketsResult(int userId, ArrayList<TicketDTO> ticketDTOs) {
    this.userId = userId;
    this.ticketDTOs = ticketDTOs;
  }

  public int getUserId() {
    return userId;
  }

  public ArrayList<TicketDTO> getTicketDTOs() {
    return ticketDTOs;
  }
}

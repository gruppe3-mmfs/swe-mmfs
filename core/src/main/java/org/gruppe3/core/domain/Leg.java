package org.gruppe3.core.domain;

import java.time.LocalDateTime;

public class Leg {

  private String mode; // e.g., "bus", "rail", "walk"
  private String lineName; // e.g., "R12", "51"
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private Location from;
  private Location to;

  public Leg(
      String mode,
      String lineName,
      LocalDateTime departureTime,
      LocalDateTime arrivalTime,
      Location from,
      Location to) {
    this.mode = mode;
    this.lineName = lineName;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.from = from;
    this.to = to;
  }

  public String getMode() {
    return mode;
  }

  public String getLineName() {
    return lineName;
  }

  public LocalDateTime getDepartureTime() {
    return departureTime;
  }

  public LocalDateTime getArrivalTime() {
    return arrivalTime;
  }

  public Location getFrom() {
    return from;
  }

  public Location getTo() {
    return to;
  }
}

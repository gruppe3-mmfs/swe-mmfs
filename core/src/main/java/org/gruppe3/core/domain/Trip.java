package org.gruppe3.core.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Trip {

  private String id;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private Location fromLocation;
  private Location toLocation;
  private ArrayList<Leg> legs;

  public Trip(Location fromLocation, Location toLocation) {
    this.fromLocation = fromLocation;
    this.toLocation = toLocation;
  }

  public Trip(
      String id,
      LocalDateTime departureTime,
      LocalDateTime arrivalTime,
      Location fromLocation,
      Location toLocation,
      ArrayList<Leg> legs) {
    this.id = id;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.fromLocation = fromLocation;
    this.toLocation = toLocation;
    this.legs = legs != null ? legs : new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public LocalDateTime getDepartureTime() {
    return departureTime;
  }

  public LocalDateTime getArrivalTime() {
    return arrivalTime;
  }

  public Location getFromLocation() {
    return fromLocation;
  }

  public Location getToLocation() {
    return toLocation;
  }

  public ArrayList<Leg> getLegs() {
    return new ArrayList<>(legs);
  }
}

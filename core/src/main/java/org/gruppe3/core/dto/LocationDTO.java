package org.gruppe3.core.dto;

public class LocationDTO {

  private final String name;
  private final double latitude;
  private final double longitude;
  private final String description;

  public LocationDTO(String name, double latitude, double longitude, String description) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getDescription() {
    return description;
  }
}

package org.gruppe3.core.dto;

public class SearchLocationRequest {

  private final String location;

  public SearchLocationRequest(String location) {
    this.location = location;
  }

  public String getLocation() {
    return location;
  }
}

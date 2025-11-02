package org.gruppe3.core.dto;

import java.util.ArrayList;

public class SearchLocationResult {

  private final ArrayList<LocationDTO> locationDTOs;

  public SearchLocationResult(ArrayList<LocationDTO> locationDTOs) {
    this.locationDTOs = locationDTOs;
  }

  public ArrayList<LocationDTO> getLocationDTOs() {
    return locationDTOs;
  }
}

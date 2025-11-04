package org.gruppe3.core.dto;

import java.util.ArrayList;
import org.gruppe3.core.domain.Location;

public class SearchLocationResult {

  private final ArrayList<Location> locations;

  public SearchLocationResult(ArrayList<Location> locations) {
    this.locations = locations;
  }

  public ArrayList<Location> getLocations() {
    return locations;
  }
}

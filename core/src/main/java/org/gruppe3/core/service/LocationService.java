package org.gruppe3.core.service;

import java.util.ArrayList;
import org.gruppe3.core.domain.Location;
import org.gruppe3.core.dto.LocationDTO;
import org.gruppe3.core.dto.SearchLocationRequest;
import org.gruppe3.core.dto.SearchLocationResult;
import org.gruppe3.core.exception.LocationAPIException;
import org.gruppe3.core.port.out.LocationAPIPort;

public class LocationService {

  private LocationAPIPort locationAPI;

  public LocationService(LocationAPIPort locationAPI) {
    this.locationAPI = locationAPI;
  }

  public SearchLocationResult searchLocations(SearchLocationRequest request)
      throws LocationAPIException {

    ArrayList<Location> foundLocations = locationAPI.searchLocations(request.getLocation());

    ArrayList<LocationDTO> foundLocationsResult = new ArrayList<>();

    for (Location location : foundLocations) {
      LocationDTO locationDTO =
          new LocationDTO(
              location.getId(),
              location.getName(),
              location.getLatitude(),
              location.getLongitude(),
              location.getDescription());
      foundLocationsResult.add(locationDTO);
    }

    SearchLocationResult result = new SearchLocationResult(foundLocationsResult);

    return result;
  }
}

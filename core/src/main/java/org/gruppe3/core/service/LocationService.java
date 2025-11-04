package org.gruppe3.core.service;

import java.util.ArrayList;
import org.gruppe3.core.domain.Location;
import org.gruppe3.core.dto.SearchLocationRequest;
import org.gruppe3.core.dto.SearchLocationResult;
import org.gruppe3.core.exception.LocationAPIException;
import org.gruppe3.core.port.out.LocationRepositoryPort;

public class LocationService {

  private LocationRepositoryPort locationRepository;

  public LocationService(LocationRepositoryPort locationRepository) {
    this.locationRepository = locationRepository;
  }

  public SearchLocationResult searchLocations(SearchLocationRequest request)
      throws LocationAPIException {

    ArrayList<Location> foundLocationResult =
        locationRepository.searchLocations(request.getLocation());
    SearchLocationResult result = new SearchLocationResult(foundLocationResult);

    return result;
  }
}

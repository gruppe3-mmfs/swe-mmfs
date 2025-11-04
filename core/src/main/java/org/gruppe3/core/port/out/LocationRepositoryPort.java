package org.gruppe3.core.port.out;

import java.util.ArrayList;
import org.gruppe3.core.domain.Location;
import org.gruppe3.core.exception.TripRepositoryException;

public interface LocationRepositoryPort {

  ArrayList<Location> searchLocations(String query) throws TripRepositoryException;
}

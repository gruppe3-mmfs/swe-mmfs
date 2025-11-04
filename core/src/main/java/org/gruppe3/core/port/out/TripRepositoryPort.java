package org.gruppe3.core.port.out;

import java.util.List;
import org.gruppe3.core.domain.Location;
import org.gruppe3.core.domain.Trip;
import org.gruppe3.core.exception.TripRepositoryException;

public interface TripRepositoryPort {

  List<Trip> searchTrips(Location from, Location to) throws TripRepositoryException;
}

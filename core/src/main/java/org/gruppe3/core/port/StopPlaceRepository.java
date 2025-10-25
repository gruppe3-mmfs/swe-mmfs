package org.gruppe3.core.port;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.gruppe3.core.domain.StopPlace;

public interface StopPlaceRepository {
  CompletableFuture<List<StopPlace>> searchStopPlaces(String query);

  CompletableFuture<StopPlace> getStopPlaceDetails(String id);
}

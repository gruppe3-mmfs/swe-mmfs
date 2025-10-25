package org.gruppe3.api.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.gruppe3.core.domain.StopPlace;
import org.gruppe3.core.port.StopPlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnturClient implements StopPlaceRepository {
  private static final Logger logger = LoggerFactory.getLogger(EnturClient.class);
  private static final String CLIENT_NAME = "hiof_utvikling-gruppe3";
  private static final String GRAPHQL_URL = "https://api.entur.io/journeyplanner/1.0/index/graphql";
  private static final HttpClient httpClient = HttpClient.newHttpClient();
  private static final ObjectMapper objectMapper = new ObjectMapper();

  // GraphQL query for searching stop places
  private static final String SEARCH_QUERY =
      """
      query($query: String!) {
        search(input: $query) {
          places {
            ... on StopPlace {
              id
              name
              latitude
              longitude
              description
            }
          }
        }
      }
      """;

  // GraphQL query for stop place details
  private static final String DETAILS_QUERY =
      """
      query($id: String!) {
        stopPlace(id: $id) {
          id
          name
          latitude
          longitude
          description
        }
      }
      """;

  @Override
  public CompletableFuture<List<StopPlace>> searchStopPlaces(String query) {
    String graphqlBody =
        """
        {
          "query": %s,
          "variables": {
            "query": "%s"
          }
        }
        """
            .formatted(SEARCH_QUERY, query.replace("\"", "\\\"")); // Escape quotes in query

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(GRAPHQL_URL))
            .header("Content-Type", "application/json")
            .header("ET-Client-Name", CLIENT_NAME)
            .POST(HttpRequest.BodyPublishers.ofString(graphqlBody))
            .build();

    return httpClient
        .sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(
            response -> {
              if (response.statusCode() != 200) {
                logger.error(
                    "Geocoder GraphQL error: HTTP {} - {}", response.statusCode(), response.body());
                throw new RuntimeException("Geocoder API error: HTTP " + response.statusCode());
              }
              return response.body();
            })
        .thenApply(this::parseGraphQLSearchResponse);
  }

  @Override
  public CompletableFuture<StopPlace> getStopPlaceDetails(String stopPlaceId) {
    String graphqlBody =
        """
        {
          "query": %s,
          "variables": {
            "id": "%s"
          }
        }
        """
            .formatted(DETAILS_QUERY, stopPlaceId.replace("\"", "\\\"")); // Escape quotes in ID

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(GRAPHQL_URL))
            .header("Content-Type", "application/json")
            .header("ET-Client-Name", CLIENT_NAME)
            .POST(HttpRequest.BodyPublishers.ofString(graphqlBody))
            .build();

    return httpClient
        .sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(
            response -> {
              if (response.statusCode() != 200) {
                logger.error(
                    "Stop place GraphQL error for ID {}: HTTP {} - {}",
                    stopPlaceId,
                    response.statusCode(),
                    response.body());
                throw new RuntimeException("Stop place API error: HTTP " + response.statusCode());
              }
              return response.body();
            })
        .thenApply(this::parseGraphQLDetailsResponse);
  }

  private List<StopPlace> parseGraphQLSearchResponse(String body) {
    List<StopPlace> stopPlaces = new ArrayList<>();
    try {
      JsonNode json = objectMapper.readTree(body);
      JsonNode places = json.path("data").path("search").path("places");
      for (JsonNode place : places) {
        String id = place.path("id").asText();
        String name = place.path("name").asText();
        double lat = place.path("latitude").asDouble();
        double lon = place.path("longitude").asDouble();
        String description = place.path("description").asText(null);
        stopPlaces.add(new StopPlace(id, name, lat, lon, description));
      }
    } catch (Exception e) {
      logger.error("Error parsing GraphQL search response: {}", e.getMessage(), e);
      throw new RuntimeException("Error parsing search response: " + e.getMessage());
    }
    return stopPlaces;
  }

  private StopPlace parseGraphQLDetailsResponse(String body) {
    try {
      JsonNode json = objectMapper.readTree(body);
      JsonNode stopPlace = json.path("data").path("stopPlace");
      if (stopPlace.isNull()) {
        return null;
      }
      String id = stopPlace.path("id").asText();
      String name = stopPlace.path("name").asText();
      double lat = stopPlace.path("latitude").asDouble();
      double lon = stopPlace.path("longitude").asDouble();
      String description = stopPlace.path("description").asText(null);
      return new StopPlace(id, name, lat, lon, description);
    } catch (Exception e) {
      logger.error("Error parsing GraphQL details response: {}", e.getMessage(), e);
      throw new RuntimeException("Error parsing details response: " + e.getMessage());
    }
  }
}

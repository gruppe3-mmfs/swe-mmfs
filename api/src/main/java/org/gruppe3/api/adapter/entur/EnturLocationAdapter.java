package org.gruppe3.api.adapter.entur;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.gruppe3.core.domain.Location;
import org.gruppe3.core.exception.LocationAPIException;
import org.gruppe3.core.port.out.LocationRepositoryPort;

public class EnturLocationAdapter implements LocationRepositoryPort {

  private static final String CLIENT_NAME = "hiof_utvikling-gruppe3";

  private static final String API_URL =
      "https://api.entur.io/geocoder/v1/autocomplete?text=%s&lang=en&size=10";

  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;

  public EnturLocationAdapter() {
    this.httpClient = HttpClient.newHttpClient();
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public ArrayList<Location> searchLocations(String query) throws LocationAPIException {
    if (query == null || query.isBlank()) {
      return new ArrayList<>(); // Empty result for invalid input
    }

    String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
    String url = String.format(API_URL, encodedQuery);

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Accept", "application/json")
            .header("ET-Client-Name", CLIENT_NAME)
            .build();

    try {
      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new LocationAPIException(
            "EnTur API returned HTTP " + response.statusCode() + ": " + response.body());
      }

      return parseResponse(response.body());

    } catch (LocationAPIException e) {
      throw e; // Re-throw our custom exception
    } catch (Exception e) {
      throw new LocationAPIException("Failed to communicate with EnTur API", e);
    }
  }

  private ArrayList<Location> parseResponse(String jsonBody) throws LocationAPIException {
    ArrayList<Location> locations = new ArrayList<>();

    try {
      JsonNode root = objectMapper.readTree(jsonBody);
      JsonNode features = root.path("features");

      if (!features.isArray()) {
        return locations; // No results
      }

      for (JsonNode feature : features) {
        String id = feature.path("properties").path("id").asText();
        if (id.isEmpty()) continue;

        String name = feature.path("properties").path("name").asText();
        String description = feature.path("properties").path("label").asText();

        JsonNode coords = feature.path("geometry").path("coordinates");
        if (coords.size() < 2) continue;

        // GeoJSON: [longitude, latitude]
        double longitude = coords.get(0).asDouble();
        double latitude = coords.get(1).asDouble();

        locations.add(new Location(id, name, latitude, longitude, description));
      }

    } catch (Exception e) {
      throw new LocationAPIException("Failed to parse EnTur JSON response", e);
    }

    return locations;
  }
}

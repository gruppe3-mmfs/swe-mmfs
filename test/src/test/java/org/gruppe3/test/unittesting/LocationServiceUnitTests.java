package org.gruppe3.test.unittesting;

import org.gruppe3.core.domain.Location;
import org.gruppe3.core.dto.SearchLocationRequest;
import org.gruppe3.core.dto.SearchLocationResult;
import org.gruppe3.core.exception.TripRepositoryException;
import org.gruppe3.core.port.out.LocationRepositoryPort;
import org.gruppe3.core.service.LocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class LocationServiceUnitTests {

    @Mock LocationRepositoryPort locationRepositoryMock;

    @Test
    @DisplayName("searchLocations - should return correct locations")
    public void searchLocationsSuccessfully() throws TripRepositoryException {

        // Arrange
        // Definerer søkespørringen
        String searchQuery = "Oslo S";
        // Oppretter stub data som skal returneres av mock objektet og sender med i en SearchLocationRequest
        SearchLocationRequest request = new SearchLocationRequest(searchQuery);

        // Lager en liste med stub locations som mocken skal returnere
        ArrayList<Location> stubbedLocations = new ArrayList<>();
        stubbedLocations.add(new Location("Oslo S"));
        stubbedLocations.add(new Location("Halden Stasjon"));
        stubbedLocations.add(new Location("Fredrikstad Stasjon"));

        // Konfigurerer mock objektet til å returnere stubbedLocations når searchLocations kalles med søkespørringen
        Mockito.when(locationRepositoryMock.searchLocations(searchQuery)).thenReturn(stubbedLocations);

        // Oppretter LocationService med mock LocationRepositoryPort
        LocationService locationService = new LocationService(locationRepositoryMock);

        // Act
        // Kaller searchLocations på LocationService
        SearchLocationResult result = locationService.searchLocations(request);

        // Assert
        // Sjekker at resultatet inneholder de forventede locations
        Assertions.assertEquals(3, result.getLocations().size(), "Should return 3 locations");
        Assertions.assertEquals("Oslo S", result.getLocations().get(0).getName(), "First location should be 'Oslo S'");
        Assertions.assertEquals("Halden Stasjon", result.getLocations().get(1).getName(), "Second location should be 'Halden Stasjon'");
        Assertions.assertEquals("Fredrikstad Stasjon", result.getLocations().get(2).getName(), "Third location should be 'Fredrikstad Stasjon'");

        // Verifiserer at ingen andre metoder på mock objektet ble kalt
        Mockito.verifyNoMoreInteractions(locationRepositoryMock);
    }

    @Test
    @DisplayName("testTripRepositoryException - should create exception with message and cause")
    void TripRepositoryExceptionSuccessfully() {

        // Arrange
        // Kaller konstruktøren med bare mmessage
        TripRepositoryException test1 = new TripRepositoryException("Test1 message");

        // Kaller konstruktøren med både message og cause
        Exception cause = new Exception("Test2 feil");
        TripRepositoryException test2 = new TripRepositoryException("Test2 message", cause);

        // Act & assert
        // Sjekker at message og cause er satt riktig
        assert test1.getMessage().equals("Test1 message") : "Message should be 'Test1 message'";
        assert test1.getCause() == null : "Cause should be null";
        assert test2.getMessage().equals("Test2 message") : "Message should be 'Test2 message'";
        assert test2.getCause() == cause : "Cause should be the same as the one provided";
    }
}

package org.gruppe3.test;
import org.gruppe3.core.domain.User;
import org.gruppe3.core.dto.CreateUserRequest;
import org.gruppe3.core.exception.UserRepositoryException;
import org.gruppe3.core.port.out.UserRepositoryPort;
import org.gruppe3.core.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


// Denne delen av koden forteller at JUnit skal bruke Mockito-integrasjon for å håndtere mock-objekter
// Det gjør at alle @Mock-felter blir initialisert automatisk før hver test kjøres
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    // Må mocke UserRepositoryPort fordi UserService avhenger av denne for å utføre funksjonene sine
    @Mock UserRepositoryPort userRepositoryMock;

    // Tester at createUser() sender riktig data til UserRepository når en ny bruker opprettes
    @Test
    @DisplayName("createUser - should create user successfully")
    public void createUserSuccessfully() throws Exception {
        
        // Arrange
        // Her definerer vi inputdataene for testen som skal sendes til createUser-metoden
        // Dette simulerer en forespørsel (CreateUserRequest) fra en klient om å opprette en ny bruker
        CreateUserRequest request = new CreateUserRequest("Ola", "Nordmann", "12345678", "ola@nordmann.no");

        // Oppretter instansen av UserService med det mockede repositoryet
        // Dette forsikrer oss om at alle kall til userRepository skjer på vårt mock-objekt og ikke på en ekte database
        UserService userService = new UserService(userRepositoryMock);

        // Act
        // Kaller metoden vi ønsker å teste med de definerte inputdataene
        userService.createUser(request);

        // Assert
        // Her må vi verifisere at createUser-metoden i UserRepository ble kalt med riktig User-objekt
        // og at dataene i dette objektet samsvarer med det vi forventer basert på inputforespørselen
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // Verifiserer at createUser ble kalt nøyaktig én gang med et User-objekt
        Mockito.verify(userRepositoryMock, Mockito.times(1)).createUser(userCaptor.capture());

        // Henter så ut det faktiske User-objektet som ble sendt til createUser-metoden
        User capturedUser = userCaptor.getValue();

        // Kan nå verifisere at alle feltene i User-objektet er som forventet
        Assertions.assertEquals("Ola", capturedUser.getFirstName(), "First name should match the request");
        Assertions.assertEquals("Nordmann", capturedUser.getLastName(), "Last name should match the request");
        Assertions.assertEquals("12345678", capturedUser.getPhoneNumber(), "Phone number should match the request");
        Assertions.assertEquals("ola@nordmann.no", capturedUser.getEmail(), "Email should match the request");

        // Verifiserer til slutt at ingen andre metoder ble kalt
        Mockito.verifyNoMoreInteractions(userRepositoryMock);

    }

    @Test
    @DisplayName("createUser - should throw UserRepositoryException when repository fails")
    public void createUserThrowsUserRepositoryExceptionSuccessfully() throws Exception {

        // Arrange
        CreateUserRequest request = new CreateUserRequest("Kari", "Nordmann", "99999999", "kari@nordmann.no");
        UserService userService = new UserService(userRepositoryMock);

        // Lager en cause som skal brukes videre i exception
        Throwable cause = new RuntimeException("Something went horribly wrong!");

        // Mock repositoryet til å kaste UserRepositoryException med cause
        Mockito.doThrow(new UserRepositoryException("Database error", cause))
               .when(userRepositoryMock)
               .createUser(Mockito.any(User.class));

        // Act
        UserRepositoryException ex = Assertions.assertThrows(
                UserRepositoryException.class,
                () -> userService.createUser(request)
        );

        // Assert
        // Sjekker at message og cause er riktig
        Assertions.assertEquals("Database error", ex.getMessage());
        Assertions.assertEquals(cause, ex.getCause());

        // Verifiser at repositoryet ble forsøkt kalt kun en gang
        Mockito.verify(userRepositoryMock, Mockito.times(1)).createUser(Mockito.any(User.class));
    }
}

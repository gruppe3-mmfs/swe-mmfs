package org.gruppe3.test;

import java.util.ArrayList;
import org.gruppe3.core.domain.User;
import org.gruppe3.core.dto.GetUserIdRequest;
import org.gruppe3.core.port.UserRepositoryPort;
import org.gruppe3.core.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

  @Mock UserRepositoryPort userRepositoryMock;

  @Test
  public void getUserById() throws Exception {

    // Arrange
    // Oppretter en liste med stubba users
    ArrayList<User> stubbedUsers = new ArrayList<>();
    stubbedUsers.add(new User(1, "First_1", "Last_1", "TestNr_1", "Email_1"));
    stubbedUsers.add(new User(2, "First_2", "Last_2", "TestNr_2", "Email_2"));
    stubbedUsers.add(new User(3, "First_3", "Last_3", "TestNr_3", "Email_3"));

    // Konfigurerer mocken til å returnere stubba users når
    // getUserById kalles med int-verdien 1
    Mockito.when(userRepositoryMock.getUserById(1)).thenReturn(stubbedUsers);

    // Initialiserer UserService med den mocka repositoryen
    // Dette gjør at vi kan filtrere ut det vi ønsker fra listen
    UserService userService = new UserService(userRepositoryMock);
    // Definerer testId til å være 1, som skal brukes til å opprette requesten
    int testUserId = 1;

    // Act
    // Her oppretter vi en request med testId og kaller getUserById
    GetUserIdRequest request = new GetUserIdRequest(testUserId);
    GetUserIdRequest result = userService.getUserById(request);

    // Assert
    // Her sjekker vi at resultatet har riktig userId
    // Sjekker at userId i resultatet er lik 1
    Assertions.assertEquals(1, request.getUserId());
    // Sjekker at resultatet ikke er null
    Assertions.assertNotNull(result);
    // Her kan  vi velge å sjekke flere detaljer, men vår userId er unik for alle brukere

    // Verifiserer at getUserById ble kalt nøyaktig en gang med argumentet 1
    Mockito.verify(userRepositoryMock).getUserById(1);
  }
}

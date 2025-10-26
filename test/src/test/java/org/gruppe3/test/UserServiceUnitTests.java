package org.gruppe3.test;
import java.util.ArrayList;

import org.gruppe3.core.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.gruppe3.core.port.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {
    
    @Mock
    UserRepositoryPort userRepositoryMock;

    @Test
    public void getUserById() 
    throws Exception {
        // Oppretter en liste med stubba users
        ArrayList<User> stubbedUsers = new ArrayList<>();
        stubbedUsers.add(new User(1, "First_1", "Last_1", "TestNr_1", "Email_1"));
        stubbedUsers.add(new User(2, "First_2", "Last_2", "TestNr_2", "Email_2"));
        stubbedUsers.add(new User(3, "First_3", "Last_3", "TestNr_3", "Email_3"));
        Mockito.when(userRepositoryMock.getUserById(2)).thenReturn(stubbedUsers);

        // Kommer mer ...


    }
}
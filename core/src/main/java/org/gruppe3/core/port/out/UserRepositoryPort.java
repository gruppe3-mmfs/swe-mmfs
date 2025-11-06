package org.gruppe3.core.port.out;

import java.util.ArrayList;
import org.gruppe3.core.domain.User;
import org.gruppe3.core.exception.UserRepositoryException;

public interface UserRepositoryPort {

  void createUser(User user) throws UserRepositoryException;

  User getUserById(int userId) throws UserRepositoryException; // Kunne returnert UserDTO her

  ArrayList<User> getAllUsersFromDatabase()
      throws UserRepositoryException; // Kunne returnert UserDTO her

  void assignUserToFamily(int userId, int familyId) throws UserRepositoryException;
}

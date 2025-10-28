package org.gruppe3.core.port;

import java.util.ArrayList;
import org.gruppe3.core.domain.User;
import org.gruppe3.core.exception.UserRepositoryException;

public interface UserRepositoryPort {

  void createUser(User user) throws UserRepositoryException;

  User getUserById(int userId) throws UserRepositoryException;

  ArrayList<User> getAllUsersFromDatabase() throws UserRepositoryException;
}

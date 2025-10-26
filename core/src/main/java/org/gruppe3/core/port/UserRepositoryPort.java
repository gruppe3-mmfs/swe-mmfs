package org.gruppe3.core.port;

import org.gruppe3.core.domain.User;
import org.gruppe3.core.exception.UserRepositoryException;

public interface UserRepositoryPort {

  void createUser(User user) throws UserRepositoryException;
}

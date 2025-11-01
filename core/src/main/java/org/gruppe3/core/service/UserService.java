package org.gruppe3.core.service;

import org.gruppe3.core.domain.User;
import org.gruppe3.core.dto.CreateUserRequest;
import org.gruppe3.core.exception.UserRepositoryException;
import org.gruppe3.core.port.in.UserRepositoryPort;

public class UserService {

  private UserRepositoryPort userRepository;

  public UserService(UserRepositoryPort userRepository) {
    this.userRepository = userRepository;
  }

  public void createUser(CreateUserRequest request) throws UserRepositoryException {
    User user =
        new User(
            request.getFirstName(),
            request.getLastName(),
            request.getPhoneNumber(),
            request.getEmail());
    userRepository.createUser(user);
  }
}

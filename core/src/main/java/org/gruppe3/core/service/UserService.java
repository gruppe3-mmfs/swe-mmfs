package org.gruppe3.core.service;

import java.util.ArrayList;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.domain.User;
import org.gruppe3.core.dto.BuyTicketRequest;
import org.gruppe3.core.dto.BuyTicketResult;
import org.gruppe3.core.dto.CreateUserRequest;
import org.gruppe3.core.exception.UserRepositoryException;
import org.gruppe3.core.port.UserRepositoryPort;

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

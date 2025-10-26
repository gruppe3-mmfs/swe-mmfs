package org.gruppe3.core.service;

import java.util.ArrayList;
import org.gruppe3.core.domain.User;
import org.gruppe3.core.dto.CreateUserRequest;
import org.gruppe3.core.dto.GetUserIdRequest;
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

  public GetUserIdRequest getUserById(GetUserIdRequest request)
  throws UserRepositoryException {

    ArrayList<User> users = userRepository.getUserById(request.getUserId());

    ArrayList<GetUserIdRequest> userDTOs = new ArrayList<>();

    for (User user : users) {
      if (user.getUserId() == request.getUserId()) {
        GetUserIdRequest getUserIdRequest = new GetUserIdRequest(user.getUserId());
        userDTOs.add(getUserIdRequest);
      }
    }

    GetUserIdRequest result = new GetUserIdRequest(
      request.getUserId()
    );
    return result;
  }
}
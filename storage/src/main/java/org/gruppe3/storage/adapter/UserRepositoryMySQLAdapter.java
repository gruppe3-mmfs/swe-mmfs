package org.gruppe3.storage.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.gruppe3.core.domain.User;
import org.gruppe3.core.exception.UserRepositoryException;
import org.gruppe3.core.port.UserRepositoryPort;

public class UserRepositoryMySQLAdapter implements UserRepositoryPort {

  private Connection connection;

  public UserRepositoryMySQLAdapter(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void createUser(User user) throws UserRepositoryException {
    String sql = "INSERT INTO users (firstName, lastName, phoneNumber, email) VALUES (?, ?, ?, ?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, user.getFirstName());
      preparedStatement.setString(2, user.getLastName());
      preparedStatement.setString(3, user.getPhoneNumber());
      preparedStatement.setString(4, user.getEmail());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new UserRepositoryException("Could not create user in database", e);
    }
  }
}

package org.gruppe3.storage.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

  @Override
  public User getUserById(int userId) throws UserRepositoryException {
    String sql = "SELECT userId " + "FROM users " + "WHERE userId = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, userId);

      ResultSet resultSet = preparedStatement.executeQuery();
      int userIdResult = resultSet.getInt("userId");
      String firstNameResult = resultSet.getString("firstName");
      String lastNameResult = resultSet.getString("lastName");
      String phoneNumberResult = resultSet.getString("phoneNumber");
      String emailResult = resultSet.getString("email");

      User user =
          new User(userIdResult, firstNameResult, lastNameResult, phoneNumberResult, emailResult);

      return user;
    } catch (SQLException e) {
      throw new UserRepositoryException("Could not retrieve user from database", e);
    }
  }

  @Override
  public ArrayList<User> getAllUsersFromDatabase() throws UserRepositoryException {
    String sql = "SELECT * FROM users";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      ArrayList<User> users = new ArrayList<>();
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int userIdResult = resultSet.getInt("userId");
        String firstNameResult = resultSet.getString("firstName");
        String lastNameResult = resultSet.getString("lastName");
        String phoneNumberResult = resultSet.getString("phoneNumber");
        String emailResult = resultSet.getString("email");

        User user =
            new User(userIdResult, firstNameResult, lastNameResult, phoneNumberResult, emailResult);
        users.add(user);
      }

      return users;
    } catch (SQLException e) {
      throw new UserRepositoryException("Could not retrieve all users from database", e);
    }
  }
}

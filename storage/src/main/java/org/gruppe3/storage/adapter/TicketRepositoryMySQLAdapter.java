package org.gruppe3.storage.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.exception.TicketRepositoryException;
import org.gruppe3.core.port.TicketRepositoryPort;

public class TicketRepositoryMySQLAdapter implements TicketRepositoryPort {

  private Connection connection;

  public TicketRepositoryMySQLAdapter(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void createTicket(Ticket ticket) throws TicketRepositoryException {
    String sql = "INSERT INTO tickets (id) VALUES (?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, ticket.getRoute().getName());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new TicketRepositoryException("Could not create ticket in database", e);
    }
  }

  @Override
  public ArrayList<Ticket> getUserTickets(int userId) throws TicketRepositoryException {
    String sql = "SELECT id " + "FROM tickets " + "WHERE ownedId=?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, userId);

      ArrayList<Ticket> userTickets = new ArrayList<>();

      ResultSet resultSet = preparedStatement.executeQuery();
      // TODO: implement while-loop etc.
      // while (resultSet.next()) {
      //
      // }

      return userTickets;
    } catch (SQLException e) {
      throw new TicketRepositoryException(
          "Could not retrieve tickets for user with id " + userId, e);
    }
  }
}

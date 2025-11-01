package org.gruppe3.storage.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.gruppe3.core.domain.Route;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.exception.TicketRepositoryException;
import org.gruppe3.core.port.in.TicketRepositoryPort;

public class TicketRepositoryMySQLAdapter implements TicketRepositoryPort {
  private Connection connection;

  public TicketRepositoryMySQLAdapter(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void createTicket(Ticket ticket) throws TicketRepositoryException {
    String sql =
        "INSERT INTO tickets (ticketHash, ticketType, ticketRouteOrigin, ticketRouteDestination)"
            + " VALUES (?, ?, ?, ?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, ticket.getTicketHash());
      preparedStatement.setString(2, ticket.getTicketType());
      preparedStatement.setString(3, ticket.getTicketRoute().getFromName());
      preparedStatement.setString(4, ticket.getTicketRoute().getToName());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new TicketRepositoryException("Could not create ticket in database", e);
    }
  }

  @Override
  public ArrayList<Ticket> getUserTickets(int userId) throws TicketRepositoryException {
    String sql = "SELECT * " + "FROM tickets " + "WHERE ticketOwnerId = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, userId);
      ResultSet resultSet = preparedStatement.executeQuery();

      ArrayList<Ticket> tickets = new ArrayList<>();
      int ticketIdResult = resultSet.getInt("ticketId");
      String ticketHashResult = resultSet.getString("ticketHash");
      String ticketTypeResult = resultSet.getString("ticketType");
      String ticketRouteOriginResult = resultSet.getString("ticketRouteOrigin");
      String ticketRouteDestinationResult = resultSet.getString("ticketRouteDestination");
      Route ticketRoute = new Route(ticketRouteOriginResult, ticketRouteDestinationResult);

      Ticket ticket = new Ticket(ticketIdResult, ticketHashResult, ticketTypeResult, ticketRoute);
      tickets.add(ticket);

      return tickets;

    } catch (SQLException e) {
      throw new TicketRepositoryException("Could not retrieve user from database", e);
    }
  }
}

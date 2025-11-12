package org.gruppe3.storage.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.gruppe3.core.domain.Location;
import org.gruppe3.core.domain.Ticket;
import org.gruppe3.core.domain.Trip;
import org.gruppe3.core.exception.TicketRepositoryException;
import org.gruppe3.core.port.out.TicketRepositoryPort;

public class TicketRepositoryMySQLAdapter implements TicketRepositoryPort {
  private Connection connection;

  public TicketRepositoryMySQLAdapter(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void createTicket(Ticket ticket) throws TicketRepositoryException {
    String sql =
        "INSERT INTO tickets (ticketHash, ticketType, ticketTripOrigin, ticketTripDestination)"
            + " VALUES (?, ?, ?, ?)";

    String lut = "SELECT * " + "FROM ticketTypes " + "WHERE ticketType = ?";

    int ticketTypeIdFromLUT = 1; // Setter billett-type til "Normal" dersom vi ikke finner noe i LUT

    try (PreparedStatement preparedStatement = connection.prepareStatement(lut)) {
      preparedStatement.setString(1, ticket.getTicketType());
      ResultSet result = preparedStatement.executeQuery();

      if (result.next()) {
        ticketTypeIdFromLUT = result.getInt("ticketTypeId");
      }
    } catch (SQLException e) {
      throw new TicketRepositoryException(
          "No ticketTypeId found for ticketType: " + ticket.getTicketType(), e);
    }

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, ticket.getTicketHash());
      preparedStatement.setInt(2, ticketTypeIdFromLUT);
      preparedStatement.setString(3, ticket.getTicketTrip().getFromLocation().getName());
      preparedStatement.setString(4, ticket.getTicketTrip().getToLocation().getName());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new TicketRepositoryException("Could not create ticket in database", e);
    }
  }

  @Override
  public ArrayList<Ticket> getUserTickets(int userId) throws TicketRepositoryException {
    String sql = "SELECT * " + "FROM tickets " + "WHERE ticketOwnerId = ?";
    ArrayList<Ticket> tickets = new ArrayList<>();

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, userId);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (!resultSet.next()) {
          throw new TicketRepositoryException("User does not own any tickets");
        } else {
          do {
            int ticketIdResult = resultSet.getInt("ticketId");
            String ticketHashResult = resultSet.getString("ticketHash");
            String ticketTypeResult = resultSet.getString("ticketType");
            String ticketTripOriginResult = resultSet.getString("ticketTripOrigin");
            String ticketTripDestinationResult = resultSet.getString("ticketTripDestination");
            int ticketOwnerIdResult = resultSet.getInt("ticketOwnerId");
            Trip ticketTrip =
                new Trip(
                    new Location(ticketTripOriginResult),
                    new Location(ticketTripDestinationResult));

            Ticket ticket =
                new Ticket(
                    ticketIdResult,
                    ticketHashResult,
                    ticketTypeResult,
                    ticketTrip,
                    ticketOwnerIdResult);
            tickets.add(ticket);
          } while (resultSet.next());
        }
      }

      return tickets;

    } catch (SQLException e) {
      throw new TicketRepositoryException("Could not retrieve tickets from database", e);
    }
  }

  @Override
  public void buyTicket(int userId, Ticket ticket) throws TicketRepositoryException {
    String sql =
        "INSERT INTO tickets (ticketHash, ticketType, ticketTripOrigin, ticketTripDestination,"
            + " ticketOwnerId) VALUES (?, ?, ?, ?, ?)";

    String lut = "SELECT * " + "FROM ticketTypes " + "WHERE ticketType = ?";

    int ticketTypeIdFromLUT = 1; // Setter billett-type til "Normal" dersom vi ikke finner noe i LUT

    try (PreparedStatement preparedStatement = connection.prepareStatement(lut)) {
      preparedStatement.setString(1, ticket.getTicketType());
      ResultSet result = preparedStatement.executeQuery();

      if (result.next()) {
        ticketTypeIdFromLUT = result.getInt("ticketTypeId");
      }
    } catch (SQLException e) {
      throw new TicketRepositoryException(
          "No ticketTypeId found for ticketType: " + ticket.getTicketType(), e);
    }

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, ticket.getTicketHash());
      preparedStatement.setInt(2, ticketTypeIdFromLUT);
      preparedStatement.setString(3, ticket.getTicketTrip().getFromLocation().getName());
      preparedStatement.setString(4, ticket.getTicketTrip().getToLocation().getName());
      preparedStatement.setInt(5, userId);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new TicketRepositoryException("Could not create ticket in database", e);
    }
  }

  @Override
  public void shareTicket(int userId, Ticket ticket, int newOwnerUserId)
      throws TicketRepositoryException {

    // Sjekker om bruker billetten skal deles til eksisterer
    String checkNewTicketOwnerId = "SELECT userId " + "FROM users " + "WHERE userId = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(checkNewTicketOwnerId)) {
      preparedStatement.setInt(1, newOwnerUserId);
      ResultSet result = preparedStatement.executeQuery();

      if (!result.next()) {
        throw new TicketRepositoryException("Cannot share ticket with non-existent user");
      }
    } catch (SQLException e) {
      throw new TicketRepositoryException("Could not find user " + newOwnerUserId, e);
    }

    String sql = "UPDATE tickets " + "SET ticketOwnerId = ? " + "WHERE ticketId = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, newOwnerUserId);
      preparedStatement.setInt(2, ticket.getTicketId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new TicketRepositoryException("Could not share ticket in database", e);
    }
  }
}

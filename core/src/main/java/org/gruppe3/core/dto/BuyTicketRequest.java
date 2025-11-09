package org.gruppe3.core.dto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import org.gruppe3.core.exception.BuyTicketRequestException;

public class BuyTicketRequest {
  private final int userId;
  private final String ticketHash;
  private final String ticketType;
  private final String ticketTripOrigin;
  private final String ticketTripDestination;

  public BuyTicketRequest(
      int userId, String ticketType, String ticketTripOrigin, String ticketTripDestination)
      throws BuyTicketRequestException {
    this.userId = userId;
    this.ticketHash =
        hashStringSHA256(
            ticketType + ticketTripOrigin + ticketTripDestination + LocalDateTime.now().toString());
    this.ticketType = ticketType;
    this.ticketTripOrigin = ticketTripOrigin;
    this.ticketTripDestination = ticketTripDestination;
  }

  public int getUserId() {
    return userId;
  }

  public String getTicketType() {
    return ticketType;
  }

  public String getTicketTripOrigin() {
    return ticketTripOrigin;
  }

  public String getTicketTripDestination() {
    return ticketTripDestination;
  }

  public String getTicketHash() {
    return ticketHash;
  }

  private static String hashStringSHA256(String input) throws BuyTicketRequestException {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

      StringBuilder hexString = new StringBuilder();
      for (byte b : hashBytes) {
        hexString.append(String.format("%02x", b));
      }

      return hexString.toString();

    } catch (NoSuchAlgorithmException e) {
      throw new BuyTicketRequestException("Could not create ticket hash", e);
    }
  }
}

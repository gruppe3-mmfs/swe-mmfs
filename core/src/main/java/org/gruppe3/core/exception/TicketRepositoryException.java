package org.gruppe3.core.exception;

public class TicketRepositoryException extends Exception {

  public TicketRepositoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public TicketRepositoryException(String message) {
    super(message);
  }

  public static TicketRepositoryException userNotFound(int userId) {
    return new TicketRepositoryException("User not found! ID: " + userId);
  }
}

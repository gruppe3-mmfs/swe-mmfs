package org.gruppe3.core.exception;

public class TripRepositoryException extends Exception {
  public TripRepositoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public TripRepositoryException(String message) {
    super(message);
  }
}

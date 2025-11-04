package org.gruppe3.core.exception;

public class LocationAPIException extends Exception {
  public LocationAPIException(String message, Throwable cause) {
    super(message, cause);
  }

  public LocationAPIException(String message) {
    super(message);
  }
}

package org.gruppe3.core.exception;

public class MySQLDatabaseException extends RuntimeException {
    public MySQLDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

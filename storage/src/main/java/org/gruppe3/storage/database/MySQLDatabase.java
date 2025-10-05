package org.gruppe3.storage.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.gruppe3.storage.exception.MySQLDatabaseException;

public class MySQLDatabase {
  private String url;
  private String username;
  private String password;
  private Connection connection;

  public MySQLDatabase(String url, String username, String password) {
    this.url = url;
    this.username = username;
    this.password = password;
  }

  public Connection startDB() throws MySQLDatabaseException {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(url, username, password);
      return connection;
    } catch (ClassNotFoundException e) {
      throw new MySQLDatabaseException("Could not find driver class", e);
    } catch (SQLException e) {
      throw new MySQLDatabaseException("Could not connect to database", e);
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public void stopDB() throws SQLException {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new SQLException("Could not close databse", e);
    }
  }
}

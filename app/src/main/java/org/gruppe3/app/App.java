package org.gruppe3.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import org.gruppe3.storage.database.MySQLDatabase;

/** Hello world! */
public class App {

  private final static String URL = "jdbc:mysql://localhost:3306/swe_mmfs"; // database URL
  private final static String USERNAME = "username"; // database username
  private final static String PASSWORD = "password"; // database password

  public static void main(String[] args) {
    System.out.println("Hello World!");

    // Instansierer databasen med URL, brukernavn og passord
    MySQLDatabase database = new MySQLDatabase(URL, USERNAME, PASSWORD);
    Connection dbConnection = database.startDB();

  }
}
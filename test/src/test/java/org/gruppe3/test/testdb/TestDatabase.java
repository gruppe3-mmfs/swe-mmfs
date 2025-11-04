package org.gruppe3.test.testdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public abstract class TestDatabase {

    protected Connection connection;

    public abstract Connection startDB() throws Exception;
    
    public abstract void stopDB() throws Exception;

    // Oppretter user-tabellen
    public void createTables() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE users (userId INT AUTO_INCREMENT PRIMARY KEY, firstName VARCHAR(25),"
            +  "lastName VARCHAR(25), phoneNumber VARCHAR(15), email VARCHAR(50), familyId INT)");
        }
    }

    // Setter inn dummy-data i user-tabellen
    public void insertIntoUsersTable() throws Exception {
        String sql = "Insert INTO users (firstName, lastName, phoneNumber, email, familyId)" +
        "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(1, "Ola");
            preparedStatement.setString(2, "Nordmann");
            preparedStatement.setString(3, "12345678");
            preparedStatement.setString(4, "Ola@Nordmann.no");
            preparedStatement.setInt(5, 1);
        }
    }
}

    

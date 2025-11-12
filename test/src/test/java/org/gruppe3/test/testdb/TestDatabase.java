package org.gruppe3.test.testdb;

import java.sql.Connection;
import java.sql.Statement;

public abstract class TestDatabase {

    protected Connection connection;

    public abstract Connection startDB() throws Exception;
    
    public abstract void stopDB() throws Exception;

    // Oppretter user-tabellen
    public void createUserTable() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users (\r\n" + //
                                "  userId INT AUTO_INCREMENT PRIMARY KEY,\r\n" + //
                                "  firstName VARCHAR(50) NOT NULL,\r\n" + //
                                "  lastName VARCHAR(50) NOT NULL,\r\n" + //
                                "  phoneNumber VARCHAR(20) NOT NULL UNIQUE,\r\n" + //
                                "  email VARCHAR(100) NOT NULL UNIQUE,\r\n" + //
                                "\tuserFamilyId INT,\r\n" + //
                                "\tFOREIGN KEY (userFamilyId) REFERENCES family(familyId)\r\n" + //
                                ")");
        }
    }

    // Oppretter ticket-tabellen
    public void createTicketTable() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute ("CREATE TABLE IF NOT EXISTS tickets (\r\n" + //
                                "\tticketId INT AUTO_INCREMENT PRIMARY KEY,\r\n" + //
                                "\tticketHash VARCHAR(64) NOT NULL UNIQUE,\r\n" + //
                                "\tticketType INT NOT NULL,\r\n" + //
                                "\tticketTripOrigin VARCHAR(50) NOT NULL,\r\n" + //
                                "\tticketTripDestination VARCHAR(50) NOT NULL,\r\n" + //
                                "\tticketOwnerId INT,\r\n" + //
                                "\tFOREIGN KEY (ticketType) REFERENCES ticketTypes(ticketTypeId),\r\n" + //
                                "\tFOREIGN KEY (ticketOwnerId) REFERENCES users(userId)\r\n" + //
                                ")");
        }
    }

    // Opprettet lookup-tabellen
    public void createLookupTable() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute ("CREATE TABLE IF NOT EXISTS ticketTypes (\r\n" + //
                                "\tticketTypeId INT AUTO_INCREMENT PRIMARY KEY,\r\n" + //
                                "\tticketType VARCHAR(50) NOT NULL UNIQUE\r\n" + //
                                ")");
        }
    }

    // Oppretter family-tabellen
    public void createFamilyTable() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute ("CREATE TABLE IF NOT EXISTS family (\r\n" + //
                                "\tfamilyId INT AUTO_INCREMENT PRIMARY KEY,\r\n" + //
                                "\tfamilyName VARCHAR(50) NOT NULL\r\n" + //
                                ")");
        }
    }    

    // Setter inn dummy-data i lookup-tabellen
    public void insertIntoLookupTable() throws Exception {
        String sql1 = "INSERT INTO ticketTypes (ticketTypeId, ticketType) VALUES (1, 'Normal');";
        String sql2 = "INSERT INTO ticketTypes (ticketTypeId, ticketType) VALUES (2, 'Student');";
        String sql3 = "INSERT INTO ticketTypes (ticketTypeId, ticketType) VALUES (3, 'Senior');";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql1);
            statement.execute(sql2);
            statement.execute(sql3);
        }
    }
}

    

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
            statement.execute("CREATE TABLE IF NOT EXISTS users "
            + "(userId INT AUTO_INCREMENT PRIMARY KEY,"
            + "firstName VARCHAR(25),"
            + "lastName VARCHAR(25),"
            + "honeNumber VARCHAR(15),"
            + "email VARCHAR(50),"
            + "userFamilyId INT,"
            + "FOREIGN KEY (userFamilyId) REFERENCES family(familyId)");
        }
    }

    // Oppretter ticket-tabellen
    public void createTicketTable() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute ("CREATE TABLE IF NOT EXISTS tickets "
            + "(ticketId INT AUTO_INCREMENT PRIMARY KEY,"
            + "ticketHash VARCHAR (64) NOT NULL UNIQUE,"
            + "ticketTripOrigin VARCHAR (50) NOT NULL,"
            + "ticketTripDestination VARCHAR (50) NOT NULL,"
            + "ticketOwnerId INT, FOREIGN KEY (ticketType) REFERENCES ticketTypes(ticketTypeId)"
            + "FOREIGN KEY (ticketOwnerId) REFERENCES users(userId)");
        }
    }

    // Opprettet lookup-tabellen
    public void createLookupTable() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute ("CREATE TABLE IF NOT EXISTS ticketTypes "
            + "(ticketTypeId INT AUTO_INCREMENT PRIMARY KEY,"
            + "ticketType VARCHAR (59) NOT NULL UNIQUE,");
        }
    }

    // Setter inn dummy-data i lookup-tabellen
    public void insertIntoLookupTable() throws Exception {
        String sql1 = "INSERT IGNORE INTO ticketTypes (ticketTypeId, ticketType) VALUES (1, \"Normal\");";
        String sql2 = "INSERT IGNORE INTO ticketTypes (ticketTypeId, ticketType) VALUES (2, \"Student\");";
        String sql3 = "INSERT IGNORE INTO ticketTypes (ticketTypeId, ticketType) VALUES (3, \"Senior\");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql1);
            statement.execute(sql2);
            statement.execute(sql3);
        }
    }
}

    

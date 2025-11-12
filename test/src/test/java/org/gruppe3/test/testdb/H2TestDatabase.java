package org.gruppe3.test.testdb;

import java.sql.Connection;

public class H2TestDatabase extends TestDatabase {

    public final static String DB_NAME = "testdb";

    // "jdbc:h2:mem" betyr at databasen er i minnet og ikke lagres på disk
    // "DB_CLOSE_DELAY=-1" betyr at databasen ikke lukkes når tilkoblingen lukkes
    public final static String URL = "jdbc:h2:mem:" + DB_NAME + ";DB_CLOSE_DELAY=-1";
    public final static String USERNAME = "user";
    public final static String PASSWORD = "password";

    public Connection startDB() throws Exception {
        // Oppretter tilkobling til H2-databasen
        Class.forName("org.h2.Driver"); // Sier at vi skal bruke H2 JDBC-driveren
        connection = java.sql.DriverManager.getConnection(URL, USERNAME, PASSWORD); // Oppretter tilkobling
        return connection;
    }

    // Lukker tilkoblingen til databasen
    public void stopDB() throws Exception {
        connection.close();
    }
}

package org.gruppe3.storage.adapter;
import org.gruppe3.core.port.TicketRepositoryPort;

public class TicketRepositoryMySQLAdapter implements TicketRepositoryPort {

    // Eksempelkode for implementering av metoden
@Override
public void createTicket(String ticketDetails) {
    // Implementation for creating a ticket in MySQL database
    System.out.println("Creating ticket with details: " + ticketDetails);
    }
}

package nl.inholland.model;

import java.time.LocalDateTime;

public class Ticket {
private TicketStatus ticketStatus;
private String ticketHolder;

    public Ticket() {
        this.ticketStatus = TicketStatus.Available;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}

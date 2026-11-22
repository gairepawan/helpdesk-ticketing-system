package com.helpdesk;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class TicketServiceTest {
    @Test void createsTicketWithUniqueIdAndNewStatus() {
        TicketService service = new TicketService();
        Ticket first = service.create("Wi-Fi unavailable", Ticket.Priority.HIGH);
        Ticket second = service.create("Printer offline", Ticket.Priority.LOW);
        assertNotEquals(first.getId(), second.getId());
        assertEquals("INC0001001", first.getId());
        assertEquals(Ticket.Status.NEW, first.getStatus());
        assertEquals(2, service.count());
    }

    @Test void prioritizesCriticalTicketsFirst() {
        TicketService service = new TicketService();
        service.create("Mouse broken", Ticket.Priority.LOW);
        service.create("Network outage", Ticket.Priority.CRITICAL);
        assertEquals(Ticket.Priority.CRITICAL, service.allByPriority().get(0).getPriority());
    }

    @Test void updatesStatusAndRejectsMissingTickets() {
        TicketService service = new TicketService();
        Ticket ticket = service.create("Account locked", Ticket.Priority.MEDIUM);
        service.updateStatus(ticket.getId(), Ticket.Status.RESOLVED);
        assertEquals(Ticket.Status.RESOLVED, service.find(ticket.getId()).getStatus());
        assertThrows(NoSuchElementException.class, () -> service.find("INC9999999"));
    }

    @Test void rejectsBlankDescription() {
        TicketService service = new TicketService();
        assertThrows(IllegalArgumentException.class,
                () -> service.create("  ", Ticket.Priority.LOW));
    }

    @Test void filtersTicketsByStatusInPriorityOrder() {
        TicketService service = new TicketService();
        Ticket low = service.create("Printer", Ticket.Priority.LOW);
        Ticket high = service.create("Network", Ticket.Priority.HIGH);
        service.create("Still new", Ticket.Priority.CRITICAL);
        service.updateStatus(low.getId(), Ticket.Status.IN_PROGRESS);
        service.updateStatus(high.getId(), Ticket.Status.IN_PROGRESS);
        assertEquals(List.of(high.getId(), low.getId()), service.ticketsByStatus(Ticket.Status.IN_PROGRESS)
                .stream().map(Ticket::getId).toList());
        assertThrows(IllegalArgumentException.class, () -> service.ticketsByStatus(null));
    }
}
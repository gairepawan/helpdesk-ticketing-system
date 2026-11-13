package com.helpdesk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketService {
    private final Map<String, Ticket> tickets = new HashMap<>();
    private final AtomicInteger nextNumber = new AtomicInteger(1001);

    public Ticket create(String description, Ticket.Priority priority) {
        String id = "INC" + String.format("%07d", nextNumber.getAndIncrement());
        Ticket ticket = new Ticket(id, description, priority);
        tickets.put(id, ticket);
        return ticket;
    }

    public Ticket find(String id) {
        Ticket ticket = tickets.get(id);
        if (ticket == null) throw new NoSuchElementException("Ticket not found: " + id);
        return ticket;
    }

    public void updateStatus(String id, Ticket.Status status) {
        find(id).setStatus(status);
    }

    public List<Ticket> allByPriority() {
        List<Ticket> sorted = new ArrayList<>(tickets.values());
        sorted.sort(Comparator.comparing(Ticket::getPriority)
                .thenComparing(Ticket::getCreatedAt)
                .thenComparing(Ticket::getId));
        return sorted;
    }

    public int count() { return tickets.size(); }
}
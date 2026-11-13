package com.helpdesk;

public class App {
    public static void main(String[] args) {
        TicketService service = new TicketService();
        service.create("Cannot connect to office Wi-Fi", Ticket.Priority.HIGH);
        Ticket printer = service.create("Printer jam on floor 2", Ticket.Priority.LOW);
        service.create("Company network is down", Ticket.Priority.CRITICAL);
        service.updateStatus(printer.getId(), Ticket.Status.IN_PROGRESS);
        System.out.println("Tickets ordered by priority:");
        service.allByPriority().forEach(System.out::println);
    }
}
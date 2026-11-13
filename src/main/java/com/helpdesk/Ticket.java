package com.helpdesk;

import java.time.Instant;
import java.util.Objects;

public final class Ticket {
    public enum Priority { CRITICAL, HIGH, MEDIUM, LOW }
    public enum Status { NEW, IN_PROGRESS, ON_HOLD, RESOLVED, CLOSED }

    private final String id;
    private final String description;
    private final Priority priority;
    private final Instant createdAt;
    private Status status;

    public Ticket(String id, String description, Priority priority) {
        this.id = Objects.requireNonNull(id);
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }
        this.description = description;
        this.priority = Objects.requireNonNull(priority);
        this.createdAt = Instant.now();
        this.status = Status.NEW;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public Instant getCreatedAt() { return createdAt; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = Objects.requireNonNull(status); }

    @Override public String toString() {
        return id + " | " + priority + " | " + status + " | " + description;
    }
}
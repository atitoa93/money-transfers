package dev.atito.event;

import java.time.LocalDateTime;

public abstract class Event {
    private final String name;
    private final LocalDateTime timestamp;

    public Event(String name) {
        this.name = name;
        timestamp = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public String getTimestamp() {
        return timestamp.toString();
    }
}

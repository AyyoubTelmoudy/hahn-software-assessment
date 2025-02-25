package io.hahnsoftware.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TicketPriority {
    LOW,
    MEDIUM,
    HIGH;
    @JsonCreator
    public static TicketPriority fromString(String value) {
        for (TicketPriority priority : TicketPriority.values()) {
            if (priority.name().equalsIgnoreCase(value)) {
                return priority;
            }
        }
        return null;
    }
}

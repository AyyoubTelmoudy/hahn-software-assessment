package io.hahnsoftware.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum TicketStatus {
    NEW("New"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved");

    private final String wording;

    TicketStatus(String wording) {
        this.wording = wording;
    }
    @JsonCreator
    public static TicketStatus fromString(String value) {
        for (TicketStatus status : TicketStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
       return null;
    }
}

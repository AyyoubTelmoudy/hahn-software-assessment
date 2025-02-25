package io.hahnsoftware.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum TicketCategory {
    NETWORK("Network"),
    HARDWARE("Hardware"),
    SOFTWARE("Software"),
    OTHER("Other");

    private final String wording;

    TicketCategory(String wording) {
        this.wording = wording;
    }
    @JsonCreator
    public static TicketCategory fromString(String value) {
        for (TicketCategory category : TicketCategory.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        return null;
    }
}

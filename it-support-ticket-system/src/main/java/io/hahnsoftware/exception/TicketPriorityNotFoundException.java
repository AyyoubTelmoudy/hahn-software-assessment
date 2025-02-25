package io.hahnsoftware.exception;

public class TicketPriorityNotFoundException extends Exception{

    public TicketPriorityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
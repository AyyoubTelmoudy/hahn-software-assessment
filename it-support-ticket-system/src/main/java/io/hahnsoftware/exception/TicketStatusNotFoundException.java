package io.hahnsoftware.exception;

public class TicketStatusNotFoundException extends Exception{

    public TicketStatusNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
package io.hahnsoftware.exception;

public class TicketNotFoundException extends Exception{

    public TicketNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
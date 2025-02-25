package io.hahnsoftware.exception;

public class TicketCategoryNotFoundException extends Exception{

    public TicketCategoryNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
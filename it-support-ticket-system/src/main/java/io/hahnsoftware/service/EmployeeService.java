package io.hahnsoftware.service;

import io.hahnsoftware.dto.request.CreateTicketRequest;
import io.hahnsoftware.dto.response.GenericResponse;
import io.hahnsoftware.enums.TicketStatus;
import io.hahnsoftware.exception.TicketCategoryNotFoundException;
import io.hahnsoftware.exception.TicketPriorityNotFoundException;
import io.hahnsoftware.exception.TicketStatusNotFoundException;

public interface EmployeeService {
    GenericResponse createTicket(CreateTicketRequest createTicketRequest) throws TicketCategoryNotFoundException, TicketPriorityNotFoundException;

    GenericResponse getTicketList(Long ticketId, String status) throws TicketStatusNotFoundException;
}

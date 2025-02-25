package io.hahnsoftware.service;

import io.hahnsoftware.dto.request.AddCommentRequest;
import io.hahnsoftware.dto.request.ChangeTicketStatusRequest;
import io.hahnsoftware.dto.response.GenericResponse;
import io.hahnsoftware.exception.TicketNotFoundException;
import io.hahnsoftware.exception.TicketStatusNotFoundException;

public interface ITService {
    GenericResponse addComment(AddCommentRequest addCommentRequest) throws TicketNotFoundException;

    GenericResponse changeTicketStatus(ChangeTicketStatusRequest changeTicketStatusRequest) throws TicketNotFoundException, TicketStatusNotFoundException;

    GenericResponse getAllTicketsList(Long ticketId, String status) throws TicketStatusNotFoundException;
}

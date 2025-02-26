package io.hahnsoftware.service;

import io.hahnsoftware.dto.request.AddCommentRequest;
import io.hahnsoftware.dto.request.ChangeTicketStatusRequest;
import io.hahnsoftware.dto.response.GenericResponse;
import io.hahnsoftware.entity.Comment;
import io.hahnsoftware.entity.ITSupportMember;
import io.hahnsoftware.entity.Ticket;
import io.hahnsoftware.enums.TicketCategory;
import io.hahnsoftware.enums.TicketPriority;
import io.hahnsoftware.enums.TicketStatus;
import io.hahnsoftware.exception.TicketNotFoundException;
import io.hahnsoftware.exception.TicketStatusNotFoundException;
import io.hahnsoftware.repository.CommentRepository;
import io.hahnsoftware.repository.ITSupportMemberRepository;
import io.hahnsoftware.repository.TicketRepository;
import io.hahnsoftware.service.impl.ITServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.hahnsoftware.constant.ResponseMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
class ITServiceImplTest {

    //@Mock
    private TicketRepository ticketRepository;

    //@Mock
    private ITSupportMemberRepository itSupportMemberRepository;

    //@Mock
    private CommentRepository commentRepository;

    //@InjectMocks
    private ITServiceImpl itService;

    private Ticket ticket;
    private ITSupportMember supportMember;

    //@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitle("This is a test title");
        ticket.setDescription("This is a test ticket description");
        ticket.setStatus(TicketStatus.NEW);
        ticket.setPriority(TicketPriority.HIGH);
        ticket.setCategory(TicketCategory.HARDWARE);

        supportMember = new ITSupportMember();
        supportMember.setEmail("test@hahnsoftware.io");
    }

    //@Test
    void addComment_ShouldAddCommentSuccessfully() throws TicketNotFoundException {
        AddCommentRequest request = new AddCommentRequest("New comment", 1L);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(itSupportMemberRepository.findByEmail(any())).thenReturn(Optional.of(supportMember));

        GenericResponse response = itService.addComment(request);

        assertEquals(COMMENT_ADDED_SUCCESSFULLY, response.getMessage());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

   // @Test
    void changeTicketStatus_ShouldUpdateStatusSuccessfully() throws TicketNotFoundException, TicketStatusNotFoundException {
        ChangeTicketStatusRequest request = new ChangeTicketStatusRequest(1L, "IN_PROGRESS");
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(itSupportMemberRepository.findByEmail(any())).thenReturn(Optional.of(supportMember));

        GenericResponse response = itService.changeTicketStatus(request);

        assertEquals(TICKET_STATUS_UPDATED_SUCCESSFULLY, response.getMessage());
        verify(ticketRepository, times(1)).save(ticket);
    }

    //@Test
    void changeTicketStatus_ShouldThrowTicketStatusNotFoundException() {
        ChangeTicketStatusRequest request = new ChangeTicketStatusRequest(1L, "INVALID_STATUS");
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        assertThrows(TicketStatusNotFoundException.class, () -> itService.changeTicketStatus(request));
    }
}

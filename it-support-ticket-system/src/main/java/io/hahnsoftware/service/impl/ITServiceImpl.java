package io.hahnsoftware.service.impl;

import static io.hahnsoftware.constant.ResponseMessage.*;
import static io.hahnsoftware.enums.TicketStatus.fromString;

import io.hahnsoftware.dto.request.AddCommentRequest;
import io.hahnsoftware.dto.request.ChangeTicketStatusRequest;
import io.hahnsoftware.dto.response.*;
import io.hahnsoftware.entity.*;
import io.hahnsoftware.enums.TicketStatus;
import io.hahnsoftware.exception.TicketNotFoundException;
import io.hahnsoftware.exception.TicketStatusNotFoundException;
import io.hahnsoftware.mapper.TicketMapper;
import io.hahnsoftware.repository.CommentRepository;
import io.hahnsoftware.repository.ITSupportMemberRepository;
import io.hahnsoftware.repository.TicketRepository;
import io.hahnsoftware.service.ITService;
import io.hahnsoftware.util.Utils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ITServiceImpl implements ITService {
    private final TicketRepository ticketRepository;
    private final ITSupportMemberRepository itSupportMemberRepository;
    private final CommentRepository commentRepository;
    @Override
    public GenericResponse addComment(AddCommentRequest addCommentRequest) throws TicketNotFoundException {
        Optional<Ticket> ticketOptional=ticketRepository.findById(addCommentRequest.getTicketId());
        if(ticketOptional.isEmpty())
          throw new TicketNotFoundException(TICKET_NOT_FOUND_EXCEPTION);
        Optional<ITSupportMember> itSupportMemberOptional=itSupportMemberRepository.findByEmail(Utils.getCurrentUsername());
        Comment comment=new Comment();
        comment.setText(addCommentRequest.getComment());
        comment.setTicket(ticketOptional.get());
        comment.setItSupportMember(itSupportMemberOptional.get());
        commentRepository.save(comment);
        AddCommentResponse addCommentResponse=new AddCommentResponse();
        addCommentResponse.setMessage(COMMENT_ADDED_SUCCESSFULLY);
        return addCommentResponse;
    }

    @Override
    public GenericResponse changeTicketStatus(ChangeTicketStatusRequest changeTicketStatusRequest) throws TicketNotFoundException, TicketStatusNotFoundException {
        Optional<Ticket> ticketOptional=ticketRepository.findById(changeTicketStatusRequest.getTicketId());
        if(ticketOptional.isEmpty())
            throw new TicketNotFoundException(TICKET_NOT_FOUND_EXCEPTION);

        TicketStatus ticketStatus= fromString(changeTicketStatusRequest.getStatus());
        if(ticketStatus==null)
            throw new TicketStatusNotFoundException(TICKET_STATUS_NOT_FOUND_EXCEPTION);

        Optional<ITSupportMember> itSupportMemberOptional=itSupportMemberRepository.findByEmail(Utils.getCurrentUsername());

        TicketHist ticketHist=new TicketHist();
        ticketHist.setTicket(ticketOptional.get());
        ticketHist.setOldStatus(ticketStatus);
        ticketHist.setNewStatus(ticketStatus);
        ticketHist.setItSupportMember(itSupportMemberOptional.get());
        Ticket ticket=ticketOptional.get();
        ticket.setStatus(ticketStatus);
        ticketRepository.save(ticket);
        ChangeTicketStatusResponse changeTicketStatusResponse=new ChangeTicketStatusResponse();
        changeTicketStatusResponse.setMessage(TICKET_STATUS_UPDATED_SUCCESSFULLY);
        return changeTicketStatusResponse;
    }

    @Override
    public GenericResponse getAllTicketsList(Long ticketId, String status) throws TicketStatusNotFoundException {
        TicketStatus ticketStatus = null;
        if(status != null && !status.isBlank())
        {
            ticketStatus= fromString(status);
            if(ticketStatus==null)
                throw new TicketStatusNotFoundException(TICKET_STATUS_NOT_FOUND_EXCEPTION);
        }
        List<Ticket> ticketList;

        if (ticketId != null && ticketStatus != null) {
            Optional<Ticket> ticket = ticketRepository.findByIdAndStatus(ticketId,ticketStatus);
            ticketList = ticket.map(List::of).orElse(List.of());
        } else if (ticketId != null) {

            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
            ticketList = ticket.map(List::of).orElse(List.of());
        } else if (ticketStatus != null) {

            ticketList = ticketRepository.findByStatus(ticketStatus);
        } else {
            ticketList = ticketRepository.findAll();
        }
        List<TicketDTO> ticketDTOS=TicketMapper.ticketToTicketDTO(ticketList);
        TicketListResponse ticketListResponse=new TicketListResponse();
        ticketListResponse.setTickets(ticketDTOS);
        ticketListResponse.setMessage(TICKET_LIST_RETURNED_SUCCESSFULLY);
        return ticketListResponse;
    }
}

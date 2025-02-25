package io.hahnsoftware.mapper;

import io.hahnsoftware.dto.response.TicketDTO;
import io.hahnsoftware.entity.Ticket;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class TicketMapper {

    public static TicketDTO ticketToTicketDTO(Ticket ticket){
        TicketDTO ticketDTO=new TicketDTO();
        BeanUtils.copyProperties(ticket,ticketDTO);
        ticketDTO.setComments(CommentMapper.commentToCommentDTO(ticket.getComments()));
        return ticketDTO;
    }
    public static List<TicketDTO> ticketToTicketDTO(List<Ticket> tickets){
        List<TicketDTO> ticketDTOS=new ArrayList<>();
        for (Ticket ticket:tickets)
        {
            ticketDTOS.add(ticketToTicketDTO(ticket));
        }
        return ticketDTOS;
    }
}

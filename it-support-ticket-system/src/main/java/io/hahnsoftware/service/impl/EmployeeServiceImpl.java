package io.hahnsoftware.service.impl;

import static io.hahnsoftware.constant.ResponseMessage.*;
import static io.hahnsoftware.enums.TicketStatus.*;
import io.hahnsoftware.dto.request.CreateTicketRequest;
import io.hahnsoftware.dto.response.CreateTicketResponse;
import io.hahnsoftware.dto.response.GenericResponse;
import io.hahnsoftware.dto.response.TicketDTO;
import io.hahnsoftware.dto.response.TicketListResponse;
import io.hahnsoftware.entity.Employee;
import io.hahnsoftware.entity.Ticket;

import io.hahnsoftware.enums.TicketCategory;
import io.hahnsoftware.enums.TicketPriority;
import io.hahnsoftware.enums.TicketStatus;
import io.hahnsoftware.exception.TicketCategoryNotFoundException;
import io.hahnsoftware.exception.TicketPriorityNotFoundException;
import io.hahnsoftware.exception.TicketStatusNotFoundException;
import io.hahnsoftware.mapper.TicketMapper;
import io.hahnsoftware.repository.EmployeeRepository;
import io.hahnsoftware.repository.TicketRepository;
import io.hahnsoftware.service.EmployeeService;
import io.hahnsoftware.util.Utils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final TicketRepository ticketRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public CreateTicketResponse createTicket(CreateTicketRequest createTicketRequest) throws TicketCategoryNotFoundException, TicketPriorityNotFoundException {

        TicketPriority priority=TicketPriority.fromString(createTicketRequest.getPriority());
        if(priority==null)
           throw new TicketPriorityNotFoundException(TICKET_CATEGORY_NOT_FOUND_EXCEPTION);

        TicketCategory category=TicketCategory.fromString(createTicketRequest.getCategory());
        if(category==null)
            throw new TicketCategoryNotFoundException(TICKET_CATEGORY_NOT_FOUND_EXCEPTION);

        Optional<Employee> employee=employeeRepository.findByEmail(Utils.getCurrentUsername());

        Ticket ticket=new Ticket();
        BeanUtils.copyProperties(createTicketRequest,ticket);

        ticket.setStatus(NEW);
        ticket.setEmployee(employee.get());
        ticket.setPriority(priority);
        ticket.setCategory(category);
        ticketRepository.save(ticket);

        CreateTicketResponse createTicketResponse=new CreateTicketResponse();
        createTicketResponse.setMessage(TICKET_CREATION_SUCCESS);
        return createTicketResponse;
    }

    @Override
    public GenericResponse getTicketList(Long ticketId, String status) throws TicketStatusNotFoundException {
        TicketStatus ticketStatus = null;
       if(status != null && !status.isBlank())
       {
           ticketStatus= fromString(status);
           if(ticketStatus==null)
               throw new TicketStatusNotFoundException(TICKET_STATUS_NOT_FOUND_EXCEPTION);
       }
        List<Ticket> ticketList;
        Optional<Employee> employeeOptional=employeeRepository.findByEmail(Utils.getCurrentUsername());
        Employee employee=employeeOptional.get();

        if (ticketId != null && ticketStatus != null) {
            Optional<Ticket> ticket = ticketRepository.findByIdAndEmployeeAndStatus(ticketId, employee,ticketStatus);
            ticketList = ticket.map(List::of).orElse(List.of());
        } else if (ticketId != null) {

            Optional<Ticket> ticket = ticketRepository.findByIdAndEmployee(ticketId, employee);
            ticketList = ticket.map(List::of).orElse(List.of());
        } else if (ticketStatus != null) {

            ticketList = ticketRepository.findByEmployeeAndStatus(employee, ticketStatus);
        } else {
            ticketList = ticketRepository.findByEmployee(employee);
        }

        List<TicketDTO> ticketDTOS=TicketMapper.ticketToTicketDTO(ticketList);
        TicketListResponse ticketListResponse=new TicketListResponse();
        ticketListResponse.setTickets(ticketDTOS);
        ticketListResponse.setMessage(TICKET_LIST_RETURNED_SUCCESSFULLY);
        return ticketListResponse;
    }
}

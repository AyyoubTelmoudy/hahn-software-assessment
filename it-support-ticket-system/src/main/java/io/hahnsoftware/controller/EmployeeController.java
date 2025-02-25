package io.hahnsoftware.controller;


import io.hahnsoftware.dto.request.CreateTicketRequest;
import io.hahnsoftware.dto.response.GenericResponse;
import io.hahnsoftware.exception.TicketCategoryNotFoundException;
import io.hahnsoftware.exception.TicketPriorityNotFoundException;
import io.hahnsoftware.exception.TicketStatusNotFoundException;
import io.hahnsoftware.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create-ticket")
    public ResponseEntity<GenericResponse> createTicket(@RequestBody @Valid CreateTicketRequest createTicketRequest) throws TicketCategoryNotFoundException, TicketPriorityNotFoundException {
        return new ResponseEntity<>(employeeService.createTicket(createTicketRequest), HttpStatus.OK);
    }
    @GetMapping("/ticket-list")
    public ResponseEntity<GenericResponse> getTicketList(@RequestParam(required = false) Long id,@RequestParam(required = false) String status) throws TicketStatusNotFoundException {
        return new ResponseEntity<>(employeeService.getTicketList(id,status), HttpStatus.OK);
    }
}

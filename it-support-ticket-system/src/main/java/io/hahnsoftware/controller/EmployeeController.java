package io.hahnsoftware.controller;


import io.hahnsoftware.dto.request.CreateTicketRequest;
import io.hahnsoftware.dto.response.GenericResponse;
import io.hahnsoftware.exception.TicketCategoryNotFoundException;
import io.hahnsoftware.exception.TicketPriorityNotFoundException;
import io.hahnsoftware.exception.TicketStatusNotFoundException;
import io.hahnsoftware.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@Tag(name = "Employee", description = "Endpoints for employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @Operation(
            summary = "Create a support ticket",
            description = "This API allows an employee to create a new ticket to it-support team",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
            }
    )
    @PostMapping("/create-ticket")
    public ResponseEntity<GenericResponse> createTicket(@RequestBody @Valid CreateTicketRequest createTicketRequest) throws TicketCategoryNotFoundException, TicketPriorityNotFoundException {
        return new ResponseEntity<>(employeeService.createTicket(createTicketRequest), HttpStatus.OK);
    }

    @Operation(
            summary = "Get a list of it-support tickets",
            description = "This API allows an employee to retrieve a list of tickets he created based on ID or status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket list retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Ticket status not found", content = @Content)
            }
    )
    @GetMapping("/ticket-list")
    public ResponseEntity<GenericResponse> getTicketList(@RequestParam(required = false) Long id,@RequestParam(required = false) String status) throws TicketStatusNotFoundException {
        return new ResponseEntity<>(employeeService.getTicketList(id,status), HttpStatus.OK);
    }
}

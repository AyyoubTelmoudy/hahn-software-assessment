package io.hahnsoftware.controller;


import io.hahnsoftware.dto.request.AddCommentRequest;
import io.hahnsoftware.dto.request.ChangeTicketStatusRequest;
import io.hahnsoftware.dto.response.GenericResponse;
import io.hahnsoftware.exception.TicketNotFoundException;
import io.hahnsoftware.exception.TicketStatusNotFoundException;
import io.hahnsoftware.service.ITService;
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
@RequestMapping("/api/v1/it-support")
@Tag(name = "IT Support", description = "Endpoints for managing IT support tickets")
public class ITController {
    @Autowired
    private  ITService itService;

    @Operation(
            summary = "Add a comment to a ticket",
            description = "This API allows IT-support members to add a comment to an existing support ticket",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comment added successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content)
            }
    )
    @PostMapping("/add-comment")
    public ResponseEntity<GenericResponse> addComment(@RequestBody @Valid AddCommentRequest addCommentRequest) throws TicketNotFoundException {
        return new ResponseEntity<>(itService.addComment(addCommentRequest), HttpStatus.OK);
    }

    @Operation(
            summary = "Change ticket status",
            description = "This API allows it-support members to update the status of an existing support ticket",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket status updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Invalid status change request", content = @Content)
            }
    )
    @PostMapping("/change-ticket-status")
    public ResponseEntity<GenericResponse> changeTicketStatus(@RequestBody @Valid ChangeTicketStatusRequest changeTicketStatusRequest) throws TicketNotFoundException , TicketStatusNotFoundException {
        return new ResponseEntity<>(itService.changeTicketStatus(changeTicketStatusRequest), HttpStatus.OK);
    }


    @Operation(
            summary = "Retrieve all tickets",
            description = "This API allows it-support members to fetch a list of all support tickets, optionally filtered by ID or status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of tickets retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content)
            }
    )
    @GetMapping("/all-ticket-list")
    public ResponseEntity<GenericResponse> getAllTicketsList(@RequestParam(required = false) Long id,@RequestParam(required = false) String status) throws TicketStatusNotFoundException {
        return new ResponseEntity<>(itService.getAllTicketsList(id,status), HttpStatus.OK);
    }

}

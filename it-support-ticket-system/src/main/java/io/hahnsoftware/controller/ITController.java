package io.hahnsoftware.controller;


import io.hahnsoftware.dto.request.AddCommentRequest;
import io.hahnsoftware.dto.request.ChangeTicketStatusRequest;
import io.hahnsoftware.dto.response.GenericResponse;
import io.hahnsoftware.exception.TicketNotFoundException;
import io.hahnsoftware.exception.TicketStatusNotFoundException;
import io.hahnsoftware.service.ITService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/it-support")
public class ITController {
    @Autowired
    private  ITService itService;
    @PostMapping("/add-comment")
    public ResponseEntity<GenericResponse> addComment(@RequestBody @Valid AddCommentRequest addCommentRequest) throws TicketNotFoundException {
        return new ResponseEntity<>(itService.addComment(addCommentRequest), HttpStatus.OK);
    }

    @PostMapping("/change-ticket-status")
    public ResponseEntity<GenericResponse> changeTicketStatus(@RequestBody @Valid ChangeTicketStatusRequest changeTicketStatusRequest) throws TicketNotFoundException , TicketStatusNotFoundException {
        return new ResponseEntity<>(itService.changeTicketStatus(changeTicketStatusRequest), HttpStatus.OK);
    }
    @GetMapping("/all-ticket-list")
    public ResponseEntity<GenericResponse> getAllTicketsList(@RequestParam(required = false) Long id,@RequestParam(required = false) String status) throws TicketStatusNotFoundException {
        return new ResponseEntity<>(itService.getAllTicketsList(id,status), HttpStatus.OK);
    }

}

package io.hahnsoftware.exception;

import io.hahnsoftware.constant.ResponseMessage;
import io.hahnsoftware.dto.response.AuthenticationResponse;
import io.hahnsoftware.dto.response.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,WebRequest request){
        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(),error.getDefaultMessage()));
        return new ResponseEntity<>(errors,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        GenericResponse genericResponse=new AuthenticationResponse();
        genericResponse.setMessage(ResponseMessage.MALFORMED_JSON_REQUEST_BODY);
        return new ResponseEntity<>(genericResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketCategoryNotFoundException.class)
    public ResponseEntity<Object> handleTicketCategoryNotFoundException(TicketCategoryNotFoundException ex) {
        GenericResponse genericResponse=new GenericResponse();
        genericResponse.setMessage(ResponseMessage.TICKET_CATEGORY_NOT_FOUND_EXCEPTION);
        return new ResponseEntity<>(genericResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketPriorityNotFoundException.class)
    public ResponseEntity<Object> handleTicketPriorityNotFoundException(TicketPriorityNotFoundException ex) {
        GenericResponse genericResponse=new GenericResponse();
        genericResponse.setMessage(ResponseMessage.TICKET_PRIORITY_NOT_FOUND_EXCEPTION);
        return new ResponseEntity<>(genericResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TicketStatusNotFoundException.class)
    public ResponseEntity<Object> handleTicketStatusNotFoundException(TicketStatusNotFoundException ex) {
        GenericResponse genericResponse=new GenericResponse();
        genericResponse.setMessage(ResponseMessage.TICKET_STATUS_NOT_FOUND_EXCEPTION);
        return new ResponseEntity<>(genericResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<Object> handleTicketNotFoundException(TicketNotFoundException ex) {
        GenericResponse genericResponse=new GenericResponse();
        genericResponse.setMessage(ResponseMessage.TICKET_NOT_FOUND_EXCEPTION);
        return new ResponseEntity<>(genericResponse,HttpStatus.BAD_REQUEST);
    }



}
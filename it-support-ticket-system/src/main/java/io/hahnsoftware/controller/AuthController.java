package io.hahnsoftware.controller;


import io.hahnsoftware.dto.request.AuthenticationRequest;
import io.hahnsoftware.dto.response.GenericResponse;
import io.hahnsoftware.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController{
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest)
    {
       return new ResponseEntity<>(authService.login(authenticationRequest), HttpStatus.OK);
    }
}
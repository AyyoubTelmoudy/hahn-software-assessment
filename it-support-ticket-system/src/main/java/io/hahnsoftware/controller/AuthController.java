package io.hahnsoftware.controller;


import io.hahnsoftware.dto.request.AuthenticationRequest;
import io.hahnsoftware.dto.response.GenericResponse;
import io.hahnsoftware.service.AuthService;
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
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController{
    @Autowired
    private AuthService authService;



    @Operation(
            summary = "User login",
            description = "This API allows users to authenticate and receive an access token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid login request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Incorrect credentials", content = @Content)
            }
    )
    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest)
    {
       return new ResponseEntity<>(authService.login(authenticationRequest), HttpStatus.OK);
    }
}
package io.hahnsoftware.service;

import io.hahnsoftware.dto.request.AuthenticationRequest;
import io.hahnsoftware.dto.response.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}

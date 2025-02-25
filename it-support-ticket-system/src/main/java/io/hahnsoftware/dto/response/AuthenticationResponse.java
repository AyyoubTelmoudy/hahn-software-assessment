package io.hahnsoftware.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationResponse extends GenericResponse {
    private String accessToken;
    private List<String> roles;
}

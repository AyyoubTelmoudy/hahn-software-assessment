package io.hahnsoftware.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationRequest {
    @NotNull
    @NotBlank
    @Size(min = 3,max = 50)
    private String username;
    @NotNull
    @NotBlank
    @Size(min = 3,max = 20)
    private String password;
}

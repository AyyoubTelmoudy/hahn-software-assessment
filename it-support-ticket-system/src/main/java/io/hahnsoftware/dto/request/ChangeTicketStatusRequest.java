package io.hahnsoftware.dto.request;

import io.hahnsoftware.dto.response.GenericResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeTicketStatusRequest extends GenericResponse {
    @NotNull
    private Long ticketId;
    @NotNull
    @NotBlank
    @Size(min = 3,max = 11)
    private String status;
}

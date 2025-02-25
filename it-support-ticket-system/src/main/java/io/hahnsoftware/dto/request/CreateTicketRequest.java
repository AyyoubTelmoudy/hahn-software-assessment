package io.hahnsoftware.dto.request;

import io.hahnsoftware.enums.TicketCategory;
import io.hahnsoftware.enums.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTicketRequest {
    @NotNull
    @NotBlank
    @Size(min = 1)
    private String title;

    @NotNull
    @NotBlank
    @Size(min = 1)
    private String description;

    @NotNull
    @NotBlank
    @Size(min = 5,max = 8)
    private String category;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 6)
    private String priority;
}

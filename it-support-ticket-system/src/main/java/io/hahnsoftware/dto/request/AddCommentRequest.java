package io.hahnsoftware.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddCommentRequest {
    @NotNull
    @NotBlank
    @Size(min = 1)
    private String comment;

    @NotNull
    private Long ticketId;
}

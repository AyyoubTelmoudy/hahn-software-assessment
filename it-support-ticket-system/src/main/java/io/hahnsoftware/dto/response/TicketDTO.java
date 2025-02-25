package io.hahnsoftware.dto.response;

import io.hahnsoftware.entity.Comment;
import io.hahnsoftware.entity.Employee;
import io.hahnsoftware.entity.TicketHist;
import io.hahnsoftware.enums.TicketCategory;
import io.hahnsoftware.enums.TicketPriority;
import io.hahnsoftware.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDTO {
    private Long id;
    private String title;
    private String description;
    private TicketCategory category;
    private TicketStatus status;
    private TicketPriority priority;
    private List<CommentDTO> comments;
}

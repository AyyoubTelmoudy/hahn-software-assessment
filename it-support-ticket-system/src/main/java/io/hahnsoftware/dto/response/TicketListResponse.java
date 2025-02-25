package io.hahnsoftware.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketListResponse extends GenericResponse{
    private List<TicketDTO> tickets;
}

package io.hahnsoftware.entity;


import io.hahnsoftware.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class TicketHist extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Ticket ticket;
    private TicketStatus oldStatus;
    private TicketStatus newStatus;
    @ManyToOne
    private ITSupportMember itSupportMember;
}

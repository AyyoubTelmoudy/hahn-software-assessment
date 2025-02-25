package io.hahnsoftware.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    private ITSupportMember itSupportMember;
    @ManyToOne
    private Ticket ticket;

}
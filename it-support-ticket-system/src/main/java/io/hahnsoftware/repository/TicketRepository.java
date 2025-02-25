package io.hahnsoftware.repository;

import io.hahnsoftware.entity.Employee;
import io.hahnsoftware.entity.Ticket;
import io.hahnsoftware.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEmployee(Employee employee);
    List<Ticket> findByEmployeeAndStatus(Employee employee, TicketStatus status);
    Optional<Ticket> findByIdAndEmployee(Long id, Employee employee);
    Optional<Ticket> findByIdAndEmployeeAndStatus(Long id, Employee employee, TicketStatus status);

    Optional<Ticket> findByIdAndStatus(Long ticketId, TicketStatus ticketStatus);

    List<Ticket> findByStatus(TicketStatus ticketStatus);
}
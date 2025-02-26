package io.hahnsoftware.config;

import io.hahnsoftware.entity.Employee;
import io.hahnsoftware.entity.ITSupportMember;
import io.hahnsoftware.entity.Ticket;
import io.hahnsoftware.enums.TicketCategory;
import io.hahnsoftware.enums.TicketPriority;
import io.hahnsoftware.enums.TicketStatus;
import io.hahnsoftware.repository.EmployeeRepository;
import io.hahnsoftware.repository.ITSupportMemberRepository;
import io.hahnsoftware.repository.TicketRepository;
import io.hahnsoftware.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration
public class SetUp {
    @Bean
    public CommandLineRunner start(EmployeeRepository employeeRepository,
                                   ITSupportMemberRepository itSupportMemberRepository,
                                   BCryptPasswordEncoder bCryptPasswordEncoder,
                                   TicketRepository ticketRepository
                                   ){
        return args -> {
            Employee employee = null;
            Optional<Employee> employeeOptional=employeeRepository.findByEmail("employee@gmail.com");
             if(employeeOptional.isEmpty())
             {
                 employee=new Employee(null,"Employee","employee@gmail.com",bCryptPasswordEncoder.encode("Azerty@123"),null);
                 employee=employeeRepository.save(employee);
             }

            Optional<ITSupportMember> itSupportMemberOptional=itSupportMemberRepository.findByEmail("it_support@gmail.com");
            if(itSupportMemberOptional.isEmpty())
            {
                ITSupportMember itSupportMember=new ITSupportMember(null,"IT-Support Member","it_support@gmail.com",bCryptPasswordEncoder.encode("Azerty@123"),null);
                itSupportMemberRepository.save(itSupportMember);
            }
            Ticket ticket1=new Ticket();
            ticket1.setTitle("Vpn");
            ticket1.setDescription("I'm enable to connect");
            ticket1.setCategory(TicketCategory.NETWORK);
            ticket1.setPriority(TicketPriority.HIGH);
            ticket1.setStatus(TicketStatus.NEW);
            if(employeeOptional.isPresent())
                ticket1.setEmployee(employeeOptional.get());
            else
                ticket1.setEmployee(employee);
            ticketRepository.save(ticket1);

            Ticket ticket2=new Ticket();
            ticket2.setTitle("Vpn");
            ticket2.setDescription("I'm enable to connect");
            ticket2.setCategory(TicketCategory.NETWORK);
            ticket2.setPriority(TicketPriority.HIGH);
            ticket2.setStatus(TicketStatus.NEW);
            if(employeeOptional.isPresent())
                ticket2.setEmployee(employeeOptional.get());
            else
                ticket2.setEmployee(employee);
            ticketRepository.save(ticket2);

        };
    }
}

package io.hahnsoftware.config;

import io.hahnsoftware.entity.Employee;
import io.hahnsoftware.entity.ITSupportMember;
import io.hahnsoftware.repository.EmployeeRepository;
import io.hahnsoftware.repository.ITSupportMemberRepository;
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
                                   BCryptPasswordEncoder bCryptPasswordEncoder
                                   ){
        return args -> {
            Optional<Employee> employeeOptional=employeeRepository.findByEmail("employee@gmail.com");
             if(employeeOptional.isEmpty())
             {
                 Employee employee=new Employee(null,"Employee","employee@gmail.com",bCryptPasswordEncoder.encode("Azerty@123"),null);
                 employeeRepository.save(employee);
             }

            Optional<ITSupportMember> itSupportMemberOptional=itSupportMemberRepository.findByEmail("it_support@gmail.com");
            if(itSupportMemberOptional.isEmpty())
            {
                ITSupportMember itSupportMember=new ITSupportMember(null,"IT-Support Member","it_support@gmail.com",bCryptPasswordEncoder.encode("Azerty@123"),null);
                itSupportMemberRepository.save(itSupportMember);
            }
        };
    }
}

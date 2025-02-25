package io.hahnsoftware.service.impl;

import io.hahnsoftware.entity.Employee;
import io.hahnsoftware.entity.ITSupportMember;
import io.hahnsoftware.entity.UserDetailsImpl;
import io.hahnsoftware.repository.EmployeeRepository;
import io.hahnsoftware.repository.ITSupportMemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl  implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final ITSupportMemberRepository itSupportMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Employee> employee=employeeRepository.findByEmail(email);
        if(employee.isPresent())
        {return new UserDetailsImpl(employee.get());}
        else
        {
            Optional<ITSupportMember> itSupportMember=itSupportMemberRepository.findByEmail(email);

            return itSupportMember.map(UserDetailsImpl::new).orElse(null);
        }
    }
}
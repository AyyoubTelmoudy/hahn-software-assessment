package io.hahnsoftware.entity;

import io.hahnsoftware.enums.UserRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private List<String> roles;

    public UserDetailsImpl(Employee employee) {
        super();
        this.username=employee.getEmail();
        this.password=employee.getPassword();
        ArrayList<String> userRoles=new ArrayList<>();
        userRoles.add(UserRole.ROLE_EMPLOYEE.name());
        this.roles=userRoles;
    }
    public UserDetailsImpl(ITSupportMember itSupportMember) {
        super();
        this.username=itSupportMember.getEmail();
        this.password=itSupportMember.getPassword();
        ArrayList<String> userRoles=new ArrayList<>();
        userRoles.add(UserRole.ROLE_IT_SUPPORT.name());
        this.roles=userRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
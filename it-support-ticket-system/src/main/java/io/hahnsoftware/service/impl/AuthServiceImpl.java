package io.hahnsoftware.service.impl;


import io.hahnsoftware.constant.ResponseMessage;
import io.hahnsoftware.constant.SecurityConstants;
import io.hahnsoftware.dto.request.AuthenticationRequest;
import io.hahnsoftware.dto.response.AuthenticationResponse;
import io.hahnsoftware.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest)
    {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        Instant instant=Instant.now();
        String username=((UserDetails) authentication.getPrincipal()).getUsername();
        List<String> roles=authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        JwtClaimsSet jwtClaimsSet= JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(username)
                .claim(SecurityConstants.ROLES,roles)
                .build();
        JwtEncoderParameters jwtEncoderParameters=JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                jwtClaimsSet
        );
        Jwt jwt=jwtEncoder.encode(jwtEncoderParameters);
        String accessToken=jwt.getTokenValue();
        // Refresh token TODO
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        authenticationResponse.setRoles(roles);
        authenticationResponse.setAccessToken(accessToken);
        authenticationResponse.setMessage(ResponseMessage.AUTHENTICATION_SUCCESS);
        return authenticationResponse;
    }

}

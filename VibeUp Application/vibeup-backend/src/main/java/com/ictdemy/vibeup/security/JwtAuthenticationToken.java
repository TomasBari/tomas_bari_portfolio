package com.ictdemy.vibeup.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Claims claims;

    public JwtAuthenticationToken(Claims claims) {
        super(getAuthoritiesFromClaims(claims));
        this.claims = claims;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return claims;
    }

    @Override
    public Object getPrincipal() {
        return claims.getSubject();
    }

    private static Collection<GrantedAuthority> getAuthoritiesFromClaims(Claims claims) {
        String role = claims.get("role", String.class);
        if (role != null) {
            return List.of(() -> "ROLE_" + role);
        } else {
            return Collections.emptyList();
        }
    }
}

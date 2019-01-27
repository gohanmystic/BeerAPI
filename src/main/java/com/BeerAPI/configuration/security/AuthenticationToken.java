package com.BeerAPI.configuration.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 7996565263553661214L;
    private String accessToken;
    private String username;

    @Getter
    private Date expiredDate;

    public AuthenticationToken(final String accessToken) {
        super(null);
        this.accessToken = accessToken;
        super.setAuthenticated(false);
    }

    public AuthenticationToken(final String username,
                               final Date expiredDate,
                               final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.username = username;
        this.expiredDate = expiredDate;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {

        if (authenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted");
        }

        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {

        return accessToken;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.Authentication#getPrincipal()
     */
    @Override
    public Object getPrincipal() {

        return username;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.authentication.AbstractAuthenticationToken#eraseCredentials()
     */
    @Override
    public void eraseCredentials() {

        super.eraseCredentials();
        this.accessToken = null;
    }
}

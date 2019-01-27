package com.BeerAPI.common.support;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.BeerAPI.common.enums.Roles;
import com.BeerAPI.common.exception.TokenExpiredException;
import com.BeerAPI.common.exception.TokenFailuredException;
import com.BeerAPI.common.properties.TokenProperties;
import com.BeerAPI.configuration.security.AuthenticationToken;
import com.BeerAPI.dto.res.auth.ResCreateToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenSupport {

    private static final String ROLE = "role";

    @Autowired
    private DateTimeSupport dateTimeSupport;

    @Autowired
    private SecretSupport secretSupport;

    @Autowired
    private TokenProperties properties;

    public ResCreateToken createAccessToken(final String userId) {

        return createToken(userId,
                           Roles.ACCESS_TOKEN,
                           UUID.randomUUID().toString(),
                           properties.getRefreshTokenTimeout());
    }

    public ResCreateToken createRefreshToken(final String userId) {

        return createToken(userId,
                           Roles.REFRESH_TOKEN,
                           UUID.randomUUID().toString(),
                           properties.getRefreshTokenTimeout());
    }

    public ResCreateToken createToken(final String userId,
                                           final Roles role,
                                           final String id,
                                           final int expiredTime) {

        final Claims claims = Jwts.claims().setSubject(userId);
        claims.put(ROLE, role);

        Date expiredDate = DateUtils.addMinutes(dateTimeSupport.now(), expiredTime);
        String token = Jwts.builder().setClaims(claims)
                                     .setExpiration(expiredDate)
                                     .setId(id)
                                     .signWith(SignatureAlgorithm.HS512,
                                               secretSupport.getHS512SecretBytes())
                                     .compact();

        return ResCreateToken.builder().expiredDate(expiredDate)
                                            .token(token)
                                            .build();
    }

    public AuthenticationToken parseToken(final String accessToken) {

        AuthenticationToken authToken = null;

        try {

            final Claims claims = Jwts.parser()
                    .setSigningKeyResolver(secretSupport.getSigningKeyResolver())
                    .parseClaimsJws(accessToken).getBody();

            if (claims != null) {

                String roleName = null;
                if (claims.get(ROLE) != null) {

                    roleName = (String) claims.get(ROLE);
                }

                authToken = new AuthenticationToken(claims.getSubject(),
                                                    claims.getExpiration(),
                                                    AuthorityUtils.createAuthorityList(roleName));
            }
        } catch (final ExpiredJwtException e) {

            throw new TokenExpiredException("Token is expired.");
        } catch (final RuntimeException e) {

            throw new TokenFailuredException("Token is invalid");
        }

        return authToken;
    }
}

package com.BeerAPI.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BeerAPI.common.entity.Login;
import com.BeerAPI.common.entity.ReNewToken;
import com.BeerAPI.common.enums.MessageCode;
import com.BeerAPI.common.enums.StatusCode;
import com.BeerAPI.common.exception.TokenExpiredException;
import com.BeerAPI.common.exception.TokenFailuredException;
import com.BeerAPI.common.support.TokenSupport;
import com.BeerAPI.configuration.security.AuthenticationToken;
import com.BeerAPI.dto.req.auth.ReqLogin;
import com.BeerAPI.dto.res.auth.ResCreateToken;
import com.BeerAPI.entity.User;
import com.BeerAPI.service.AuthenticationService;
import com.BeerAPI.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenSupport tokenSupport;

    @Autowired
    private UserService userService;

    @Override
    public Login login(ReqLogin reqLogin) {

        final String username = reqLogin.getUsername();

        User employee = userService.findUserByUsername(username);

        if (employee == null) {

            return Login.builder()
                    .errorCode(StatusCode.BAD_CREDENTIALS)
                    .errorMessage(MessageCode.USERNAME_NOT_FOUND)
                    .build();
        }

        if (!passwordEncoder.matches(reqLogin.getPassword(), employee.getPassword())) {

            return Login.builder()
                        .errorCode(StatusCode.BAD_CREDENTIALS)
                        .errorMessage(MessageCode.USER_PASSWORD_ERROR)
                        .build();

        }

        ResCreateToken accessToken = tokenSupport.createAccessToken(username);
        ResCreateToken refreshToken = tokenSupport.createRefreshToken(username);

        return Login.builder()
                    .accessToken(accessToken.getToken())
                    .accessTokenExpiredDate(accessToken.getExpiredDate())
                    .refreshToken(refreshToken.getToken())
                    .refreshTokenExpiredDate(refreshToken.getExpiredDate())
                    .permissions(employee.getAuthorities())
                    .build();
    }

    @Override
    public ReNewToken renewToken(String refreshToken) {

        AuthenticationToken auth = null;
        try {
            auth = tokenSupport.parseToken(refreshToken);
        } catch (final TokenExpiredException e) {

            return ReNewToken.builder()
                    .errorCode(StatusCode.TOKEN_EXPIRED)
                    .errorMessage(MessageCode.TOKEN_EXPIRED)
                    .build();
        } catch (final TokenFailuredException e) {

            return ReNewToken.builder()
                    .errorCode(StatusCode.TOKEN_FAILURED)
                    .errorMessage(MessageCode.TOKEN_FAILURED)
                    .build();
        }

        String username = auth.getPrincipal().toString();

        ResCreateToken accessToken = tokenSupport.createAccessToken(username);

        return ReNewToken.builder()
                              .accessToken(accessToken.getToken())
                              .accessTokenExpiredDate(accessToken.getExpiredDate())
                              .build();
    }
}

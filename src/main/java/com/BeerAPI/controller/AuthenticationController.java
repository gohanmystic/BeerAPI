package com.BeerAPI.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BeerAPI.common.ApiResponse;
import com.BeerAPI.common.entity.Login;
import com.BeerAPI.common.entity.ReNewToken;
import com.BeerAPI.common.enums.MessageCode;
import com.BeerAPI.common.support.ResponseSupport;
import com.BeerAPI.dto.req.auth.ReqLogin;
import com.BeerAPI.dto.res.auth.ResLogin;
import com.BeerAPI.dto.res.auth.ResReNewToken;
import com.BeerAPI.service.AuthenticationService;

@RestController
@RequestMapping( value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE )
public class AuthenticationController {

    @Autowired
    private ResponseSupport responseSupport;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody ReqLogin reqLogin) {

        final Login login = authService.login(reqLogin);

        if (login.getErrorCode() != null) {

            return ResponseEntity.ok(
                    responseSupport.fetchError(login.getErrorCode(),
                                               login.getErrorMessage()));
        }

        final ResLogin data = modelMapper.map(login, ResLogin.class);

        return ResponseEntity.ok(
                responseSupport.fetchSuccess(data, MessageCode.SUCCESS_LOGIN));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping(value = "renew")
    public ResponseEntity<ApiResponse<?>> renewToken(
            @RequestHeader("refresh_token") final String refreshToken) {

        final ReNewToken result = authService.renewToken(refreshToken);

        if (result.getErrorCode() != null) {

            return new ResponseEntity(
                    responseSupport.fetchError(result.getErrorCode(),
                                               result.getErrorMessage()),
                    HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(
                responseSupport.fetchSuccess(
                        modelMapper.map(result, ResReNewToken.class),
                        MessageCode.SUCCESS_DATA));
    }
}
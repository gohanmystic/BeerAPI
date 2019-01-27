package com.BeerAPI.service;

import com.BeerAPI.common.entity.Login;
import com.BeerAPI.common.entity.ReNewToken;
import com.BeerAPI.dto.req.auth.ReqLogin;

public interface AuthenticationService {
    Login login(ReqLogin reqLogin);
    ReNewToken renewToken(final String refreshToken);
}

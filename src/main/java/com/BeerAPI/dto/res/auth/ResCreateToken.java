package com.BeerAPI.dto.res.auth;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResCreateToken {

    private String token;

    private Date expiredDate;
}

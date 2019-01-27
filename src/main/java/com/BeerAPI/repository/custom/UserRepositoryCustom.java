package com.BeerAPI.repository.custom;

import java.util.List;

import com.BeerAPI.dto.prm.user.PrmFetchUser;
import com.BeerAPI.entity.User;

public interface UserRepositoryCustom {

    List<User> fetchUser(PrmFetchUser fetchParam);
}

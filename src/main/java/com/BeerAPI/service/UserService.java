package com.BeerAPI.service;

import java.util.List;

import com.BeerAPI.dto.req.user.ReqCreateUser;
import com.BeerAPI.dto.req.user.ReqDeleteUser;
import com.BeerAPI.dto.req.user.ReqFetchUser;
import com.BeerAPI.dto.req.user.ReqUpdateUser;
import com.BeerAPI.entity.User;

public interface UserService {

    User findUserByUsername(String username);

    User findUserById(Integer id);

    List<User> findAllUser();

    User saveUser(ReqCreateUser reqCreateUser);

    boolean isExistUser(String username);

    boolean isExistUserById(Integer UserId);

    List<User> fetchUser(ReqFetchUser fetchRequest);

    User updateUser(ReqUpdateUser reqUpdateUser);

    boolean deleteUser(ReqDeleteUser reqDeleteUser);
}

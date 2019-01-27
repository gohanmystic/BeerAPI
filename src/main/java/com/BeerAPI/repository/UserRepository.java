package com.BeerAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BeerAPI.entity.User;
import com.BeerAPI.repository.custom.UserRepositoryCustom;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}

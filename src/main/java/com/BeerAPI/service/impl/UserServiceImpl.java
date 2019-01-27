package com.BeerAPI.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BeerAPI.common.exception.NotFoundException;
import com.BeerAPI.common.support.BeanSupport;
import com.BeerAPI.dto.prm.user.PrmFetchUser;
import com.BeerAPI.dto.req.user.ReqCreateUser;
import com.BeerAPI.dto.req.user.ReqDeleteUser;
import com.BeerAPI.dto.req.user.ReqFetchUser;
import com.BeerAPI.dto.req.user.ReqUpdateUser;
import com.BeerAPI.entity.User;
import com.BeerAPI.repository.UserRepository;
import com.BeerAPI.service.UserService;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BeanSupport beanSupport;

    @Override
    public User findUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserById(Integer id) throws AccessDeniedException {

        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new NotFoundException();
        };

        return user.get();
    }

    @Override
    public List<User> findAllUser() throws AccessDeniedException {

        return userRepository.findAll();
    }

    @Override
    public User saveUser(ReqCreateUser reqCreateUser) {

        reqCreateUser.setPassword(passwordEncoder
                                         .encode(reqCreateUser
                                                 .getPassword()));
        User employee = modelMapper.map(reqCreateUser, User.class);

        return userRepository.save(employee);
    }

    @Override
    public boolean isExistUser(String username) {

        User employee = userRepository.findByUsername(username);

        return employee != null;
    }

    @Override
    public List<User> fetchUser(ReqFetchUser fetchRequest) {

        return userRepository.fetchUser(
                modelMapper.map(fetchRequest, PrmFetchUser.class));
    }

    @Override
    public User updateUser(ReqUpdateUser reqUpdateUser) {

        Optional<User> target = userRepository.findById(reqUpdateUser.getUserId());

        if (!target.isPresent()) {
            throw new NotFoundException();
        }

        User source = modelMapper.map(reqUpdateUser, User.class);

        beanSupport.copyNonNullProperties(source, target.get());

        return userRepository.save(target.get());
    }

    @Override
    public boolean isExistUserById(Integer userId) {

        return userRepository.findById(userId).isPresent();
    }

    @Override
    public boolean deleteUser(ReqDeleteUser reqDeleteUser) {

        Integer id = reqDeleteUser.getUserId();
        userRepository.deleteById(id);

        return !userRepository.findById(id).isPresent();
    }
}

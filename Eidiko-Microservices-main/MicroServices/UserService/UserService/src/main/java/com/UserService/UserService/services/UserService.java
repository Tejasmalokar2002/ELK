package com.UserService.UserService.services;

import com.UserService.UserService.entities.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    User getUser(String userId);


}

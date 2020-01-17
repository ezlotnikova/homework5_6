package com.gmail.zlotnikova.service;

import java.util.List;

import com.gmail.zlotnikova.repository.model.User;
import com.gmail.zlotnikova.service.model.UserDTO;

public interface UserService extends GeneralService<UserDTO> {

    List<User> findAll();

    int deleteUserUnderLimitAge(int age);

    int changeStatusByAge(int lowerAgeLimit, int upperAgeLimit);

}
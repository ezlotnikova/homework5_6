package com.gmail.zlotnikova.service.impl;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.zlotnikova.repository.ConnectionRepository;
import com.gmail.zlotnikova.repository.impl.ConnectionRepositoryImpl;
import com.gmail.zlotnikova.service.NewUserService;
import com.gmail.zlotnikova.service.UserInfoService;
import com.gmail.zlotnikova.service.UserService;
import com.gmail.zlotnikova.service.model.NewUserDTO;
import com.gmail.zlotnikova.service.model.UserDTO;
import com.gmail.zlotnikova.service.model.UserInfoDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewUserServiceImpl implements NewUserService {

    private static NewUserService instance;

    private NewUserServiceImpl() {
    }

    public static NewUserService getInstance() {
        if (instance == null) {
            instance = new NewUserServiceImpl();
        }
        return instance;
    }

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    UserService userService = UserServiceImpl.getInstance();
    UserInfoService userInfoService = UserInfoServiceImpl.getInstance();

    @Override
    public NewUserDTO add(NewUserDTO newUserDTO) {
        UserDTO userDTO = UserDTO.newBuilder()
                .username(newUserDTO.getUsername())
                .password(newUserDTO.getPassword())
                .isActive(newUserDTO.getActive())
                .userGroupId(newUserDTO.getUserGroupId())
                .age(newUserDTO.getAge())
                .build();
        userDTO = userService.add(userDTO);
        UserInfoDTO userInfoDTO = UserInfoDTO.newBuilder()
                .userId(userDTO.getId())
                .address(newUserDTO.getAddress())
                .telephone(newUserDTO.getTelephone())
                .build();
        userInfoService.add(userInfoDTO);
        return newUserDTO;
    }


}

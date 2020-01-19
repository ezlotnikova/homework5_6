package com.gmail.zlotnikova.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gmail.zlotnikova.repository.ConnectionRepository;
import com.gmail.zlotnikova.repository.UserInfoRepository;
import com.gmail.zlotnikova.repository.UserRepository;
import com.gmail.zlotnikova.repository.impl.ConnectionRepositoryImpl;
import com.gmail.zlotnikova.repository.impl.UserInfoRepositoryImpl;
import com.gmail.zlotnikova.repository.impl.UserRepositoryImpl;
import com.gmail.zlotnikova.repository.model.User;
import com.gmail.zlotnikova.service.UserService;
import com.gmail.zlotnikova.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static UserService instance;

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    UserRepository userRepository = UserRepositoryImpl.getInstance();
    UserInfoRepository userInfoRepository = UserInfoRepositoryImpl.getInstance();

    @Override
    public UserDTO add(UserDTO userDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertDTOToUser(userDTO);
                user = userRepository.add(connection, user);
                userDTO = convertUserToUserDTO(user);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return userDTO;
    }

    private User convertDTOToUser(UserDTO userDTO) {
        return User.newBuilder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .isActive(userDTO.getActive())
                .userGroupId(userDTO.getUserGroupId())
                .age(userDTO.getAge())
                .build();
    }

    private UserDTO convertUserToUserDTO(User user) {
        return UserDTO.newBuilder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .isActive(user.getActive())
                .age(user.getAge())
                .build();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                users = userRepository.findAll(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return users;
    }

    @Override
    public int deleteUserUnderLimitAge(int age) {
        int rowsDeleted = 0;
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userInfoRepository.deleteUserUnderLimitAge(connection, age);
                rowsDeleted = userRepository.deleteUserUnderLimitAge(connection, age);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return rowsDeleted;
    }

    @Override
    public int changeStatusByAge(int lowerAgeLimit, int upperAgeLimit) {
        int rowsAffected = 0;
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                rowsAffected = userRepository.changeStatusByAge(connection, lowerAgeLimit, upperAgeLimit);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return rowsAffected;
    }

}